package com.revined.revined.service;

import com.revined.revined.config.JwtTokenUtil;
import com.revined.revined.exception.RefreshTokenNotFoundException;
import com.revined.revined.exception.TokenRefreshException;
import com.revined.revined.model.RefreshToken;
import com.revined.revined.model.User;
import com.revined.revined.repository.RefreshTokenRepository;
import com.revined.revined.repository.UserRepository;
import com.revined.revined.utils.Environments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @Mock
    private Environments mockEnvironments;

    @Mock
    private RefreshTokenRepository mockRefreshTokenRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private JwtTokenUtil mockJwtTokenUtil;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    void findTokenByRefreshToken() {
        User user = User
                .builder()
                .email("email")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("refresh-token")
                .expiryDate(LocalDateTime.now().plusHours(6L))
                .user(user)
                .build();

        when(mockRefreshTokenRepository.findByToken("refresh-token"))
                .thenReturn(Optional.of(refreshToken));

        when(mockJwtTokenUtil.generateToken(user))
                .thenReturn("token");

        String token = refreshTokenService.findTokenByRefreshToken("refresh-token");
        Assertions.assertEquals("token", token);
    }

    @Test
    void findTokenByRefreshTokenThrowsTokenRefreshException() {
        User user = User
                .builder()
                .email("email")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("refresh-token")
                .expiryDate(LocalDateTime.now().minusHours(6L))
                .user(user)
                .build();

        when(mockRefreshTokenRepository.findByToken("refresh-token"))
                .thenReturn(Optional.of(refreshToken));

        assertThrows(
                TokenRefreshException.class,
                () -> refreshTokenService.findTokenByRefreshToken("refresh-token")
        );

        verify(mockRefreshTokenRepository).delete(refreshToken);
    }

    @Test
    void findTokenByRefreshThrowsRefreshTokenNotFoundException() {
        when(mockRefreshTokenRepository.findByToken("refresh-token"))
                .thenReturn(Optional.empty());

        assertThrows(
                RefreshTokenNotFoundException.class,
                () -> refreshTokenService.findTokenByRefreshToken("refresh-token")
        );
    }

    @Test
    void createRefreshToken() {
        User user = User
                .builder()
                .email("email")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("refresh-token")
                .expiryDate(LocalDateTime.now().plusHours(6L))
                .user(user)
                .build();

        when(mockUserRepository.findByEmail("email"))
                .thenReturn(Optional.of(user));

        when(mockRefreshTokenRepository.save(any(RefreshToken.class)))
                .thenReturn(refreshToken);

        when(mockEnvironments.getVariable("JWT_REFRESH_EXPIRATION_IN_SECONDS"))
                .thenReturn("1160");

        String actualRefreshToken = refreshTokenService.createRefreshToken("email");

        assertEquals("refresh-token", actualRefreshToken);
    }

    @Test
    void createRefreshTokenThrowsUsernameNotFoundException() {
        when(mockUserRepository.findByEmail("email"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> refreshTokenService.createRefreshToken("email")
        );
    }

    @Test
    void deleteByUserId() {
        User user = User
                .builder()
                .email("email")
                .build();

        when(mockUserRepository.findByEmail("email"))
                .thenReturn(Optional.of(user));

        refreshTokenService.deleteByUserId("email");

        verify(mockRefreshTokenRepository).deleteByUser(user);
    }

    @Test
    void deleteByUserIdThrowsUsernameNotFoundException() {
        when(mockUserRepository.findByEmail("email"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> refreshTokenService.deleteByUserId("email")
        );
    }
}