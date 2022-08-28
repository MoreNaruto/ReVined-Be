package com.revined.revined.service;

import com.revined.revined.config.JwtTokenUtil;
import com.revined.revined.exception.PasswordDoesNotMatchException;
import com.revined.revined.model.User;
import com.revined.revined.model.enums.Roles;
import com.revined.revined.repository.UserRepository;
import com.revined.revined.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository repository;

    public String createJWTToken(String email, String password) throws Exception {
        authenticate(email, password);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(email);

        return jwtTokenUtil.generateToken(userDetails);
    }

    public void createUser(SignUpRequest request) throws Exception {
        if (!request.getPassword().equals(request.getMatchPassword())) {
            throw new PasswordDoesNotMatchException("The password and matchPassword are not the same");
        }

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bcryptPasswordEncoder.encode(request.getPassword());


        repository.save(
                User
                        .builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(encryptedPassword)
                        .active(true)
                        .role(Roles.EMPLOYEE)
                        .build()
        );
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
