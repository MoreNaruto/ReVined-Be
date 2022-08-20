package com.revined.revined.service;

import com.revined.revined.config.JwtTokenUtil;
import com.revined.revined.exception.RefreshTokenNotFoundException;
import com.revined.revined.exception.TokenRefreshException;
import com.revined.revined.model.RefreshToken;
import com.revined.revined.model.User;
import com.revined.revined.repository.RefreshTokenRepository;
import com.revined.revined.repository.UserRepository;
import com.revined.revined.utils.Environments;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private Environments environments;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String findTokenByRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> jwtTokenUtil.generateToken(user))
                .orElseThrow(
                        () -> new RefreshTokenNotFoundException("Refresh token is not in database!")
                );
    }

    public String createRefreshToken(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        RefreshToken refreshToken = RefreshToken
                .builder()
                .user(user)
                .expiryDate(LocalDateTime.now().plusSeconds(Long.parseLong(environments.getVariable("JWT_REFRESH_EXPIRATION_IN_SECONDS"))))
                .token(UUID.randomUUID().toString())
                .build();

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
        return savedToken.getToken();
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public void deleteByUserId(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        refreshTokenRepository.deleteByUser(user);
    }
}
