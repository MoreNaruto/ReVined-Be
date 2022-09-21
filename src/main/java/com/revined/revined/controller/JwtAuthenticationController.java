package com.revined.revined.controller;

import com.revined.revined.request.*;
import com.revined.revined.response.JwtResponse;
import com.revined.revined.service.JwtAuthenticationService;
import com.revined.revined.service.RefreshTokenService;
import com.revined.revined.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {
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

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ResponseEntity<String> resetPassword(@RequestParam("email") String userEmail) throws Exception{
        resetPasswordService.createResetPassword(userEmail);

        return ResponseEntity.ok("Successfully reset password");
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(@RequestParam("token") String token) throws Exception {
        resetPasswordService.changePassword(token);

        return ResponseEntity.ok("User is allowed to change password");
    }

    @RequestMapping(value = "/save-password", method = RequestMethod.POST)
    public ResponseEntity<String> savePassword(@Valid @RequestBody SavePasswordRequest request) throws Exception {
        resetPasswordService.saveNewPassword(request);

        return ResponseEntity.ok("Password has been updated");
    }

    @RequestMapping(value = "/log-out", method = RequestMethod.POST)
    public ResponseEntity<String> logoutUser(@Valid @RequestBody LogoutRequest logoutRequest) {
        refreshTokenService.deleteByUserId(logoutRequest.getEmail());
        return ResponseEntity.ok("Log out successful!");
    }
}
