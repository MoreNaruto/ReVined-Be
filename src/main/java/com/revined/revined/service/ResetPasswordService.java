package com.revined.revined.service;

import com.revined.revined.exception.PasswordDoesNotMatchException;
import com.revined.revined.exception.ResetPasswordTokenNotFoundException;
import com.revined.revined.model.ResetPassword;
import com.revined.revined.model.User;
import com.revined.revined.repository.ResetPasswordRepository;
import com.revined.revined.repository.UserRepository;
import com.revined.revined.request.SavePasswordRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ResetPasswordService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    public void createResetPassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        resetPasswordRepository.save(
                ResetPassword
                        .builder()
                        .expiryDate(LocalDateTime.now().plusHours(3))
                        .user(user)
                        .token(generateBase64String())
                        .build()
        );
    }

    public void changePassword(String token) {
        ResetPassword resetPassword = resetPasswordRepository
                .findByToken(token)
                .orElseThrow(() -> new ResetPasswordTokenNotFoundException("Token not found"));

        if (resetPassword.getExpiryDate().isAfter(LocalDateTime.now())) {
            throw new ResetPasswordTokenNotFoundException("Token has expired");
        }
    }

    public void saveNewPassword(SavePasswordRequest request) throws Exception {
        ResetPassword resetPassword = resetPasswordRepository
                .findByToken(request.getToken())
                .orElseThrow(() -> new ResetPasswordTokenNotFoundException("Token not found"));

        if (!request.getPassword().equals(request.getMatchPassword())) {
            throw new PasswordDoesNotMatchException("Passwords do not match");
        }

        User user = resetPassword.getUser();
        user.setPassword(request.getPassword());
        userRepository.save(user);
    }

    private String generateBase64String() {
        Random random = ThreadLocalRandom.current();
        byte[] r = new byte[256];
        random.nextBytes(r);
        return Base64.encodeBase64String(r);
    }
}
