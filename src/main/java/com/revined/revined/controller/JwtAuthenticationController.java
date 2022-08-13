package com.revined.revined.controller;

import com.revined.revined.request.JwtRequest;
import com.revined.revined.request.SignUpRequest;
import com.revined.revined.response.JwtResponse;
import com.revined.revined.service.JwtAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String token = jwtAuthenticationService.createJWTToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        jwtAuthenticationService.createUser(signUpRequest);

        return ResponseEntity.ok("Successfully created user");
    }
}
