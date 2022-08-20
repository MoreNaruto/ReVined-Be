package com.revined.revined.service;

import com.revined.revined.config.JwtTokenUtil;
import com.revined.revined.exception.PasswordDoesNotMatchException;
import com.revined.revined.model.User;
import com.revined.revined.model.enums.Roles;
import com.revined.revined.repository.UserRepository;
import com.revined.revined.request.SignUpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationServiceTest {
    @Mock
    private AuthenticationManager mockAuthenticationManager;

    @Mock
    private JwtTokenUtil mockJwtTokenUtil;

    @Mock
    private JwtUserDetailsService mockJwtUserDetailsService;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private JwtAuthenticationService jwtAuthenticationService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    void testCreateJWTToken() throws Exception {
        User user = User
                .builder()
                .role(Roles.ADMIN)
                .email("email")
                .password("password")
                .firstName("Bob")
                .lastName("Dole")
                .active(true)
                .build();

        when(mockAuthenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenReturn(mockAuthentication);

        when(mockJwtUserDetailsService.loadUserByUsername("email"))
                .thenReturn(user);

        when(mockJwtTokenUtil.generateToken(user))
                .thenReturn("token");

        String jwtToken = jwtAuthenticationService.createJWTToken("email", "password");
        Assertions.assertEquals(jwtToken, "token");
    }

    @Test
    void testCreateJWTTokenThrowsDisabledException() {
        when(mockAuthenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenThrow(new DisabledException("Account is disabled"));

        assertThrows(Exception.class, () -> jwtAuthenticationService.createJWTToken("email", "password"), "USER_DISABLED");
    }

    @Test
    void testCreateJWTTokenThrowsBadCredentialsException() {
        when(mockAuthenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenThrow(new BadCredentialsException("Invalid credentials given"));

        assertThrows(Exception.class, () -> jwtAuthenticationService.createJWTToken("email", "password"), "INVALID_CREDENTIALS");
    }

    @Test
    void testCreateUser() throws Exception {
        SignUpRequest request = SignUpRequest.builder()
                .role(Roles.ADMIN)
                .firstName("Bob")
                .lastName("Dole")
                .email("email")
                .password("password")
                .matchPassword("password")
                .build();

        jwtAuthenticationService.createUser(request);

        verify(mockUserRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertEquals(capturedUser.getEmail(), "email");
        assertEquals(capturedUser.getFirstName(), "Bob");
        assertEquals(capturedUser.getLastName(), "Dole");
        assertTrue(capturedUser.isActive());
        assertEquals(capturedUser.getRole(), Roles.ADMIN);
    }

    @Test
    void testCreateUserPasswordDoesNotMatchException() throws Exception {
        SignUpRequest request = SignUpRequest.builder()
                .role(Roles.ADMIN)
                .firstName("Bob")
                .lastName("Dole")
                .email("email")
                .password("password")
                .matchPassword("not-password")
                .build();

        assertThrows(
                PasswordDoesNotMatchException.class,
                () -> jwtAuthenticationService.createUser(request),
                "The password and matchPassword are not the same");
    }
}