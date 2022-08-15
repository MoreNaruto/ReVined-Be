package com.revined.revined.controller;

import com.revined.revined.request.JwtRequest;
import com.revined.revined.request.LogoutRequest;
import com.revined.revined.request.SignUpRequest;
import com.revined.revined.request.TokenRefreshRequest;
import com.revined.revined.response.JwtResponse;
import com.revined.revined.service.JwtAuthenticationService;
import com.revined.revined.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String token = jwtAuthenticationService.createJWTToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        String refreshToken = refreshTokenService.createRefreshToken(authenticationRequest.getEmail());

        return ResponseEntity.ok(new JwtResponse(token, refreshToken));
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        jwtAuthenticationService.createUser(signUpRequest);

        return ResponseEntity.ok("Successfully created user");
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) throws Exception {
        String token = refreshTokenService.findTokenByRefreshToken(request.getRefreshToken());

        return ResponseEntity.ok(new JwtResponse(token, request.getRefreshToken()));
    }

    @RequestMapping(value = "/log-out", method = RequestMethod.POST)
    public ResponseEntity<String> logoutUser(@Valid @RequestBody LogoutRequest logoutRequest) {
        refreshTokenService.deleteByUserId(logoutRequest.getEmail());
        return ResponseEntity.ok("Log out successful!");
    }
}
