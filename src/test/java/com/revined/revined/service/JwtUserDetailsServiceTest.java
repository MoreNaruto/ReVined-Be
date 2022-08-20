package com.revined.revined.service;

import com.revined.revined.model.User;
import com.revined.revined.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    void loadUserByUsername() {
        User expectedUser = User
                .builder()
                .email("email")
                .build();

        when(mockUserRepository.findByEmail("email"))
                .thenReturn(Optional.of(expectedUser));

        UserDetails actualUser = jwtUserDetailsService.loadUserByUsername("email");

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void loadUserByUsernameThrowsUsernameNotFoundException() {
        when(mockUserRepository.findByEmail("email"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> jwtUserDetailsService.loadUserByUsername("email")
        );
    }
}