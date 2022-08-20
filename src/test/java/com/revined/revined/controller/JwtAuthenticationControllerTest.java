package com.revined.revined.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revined.revined.advice.RefreshTokenNotFoundControllerAdvice;
import com.revined.revined.advice.TokenControllerAdvice;
import com.revined.revined.advice.UserNotFoundControllerAdvice;
import com.revined.revined.request.JwtRequest;
import com.revined.revined.request.LogoutRequest;
import com.revined.revined.request.SignUpRequest;
import com.revined.revined.request.TokenRefreshRequest;
import com.revined.revined.response.JwtResponse;
import com.revined.revined.service.JwtAuthenticationService;
import com.revined.revined.service.RefreshTokenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private JwtAuthenticationService mockJwtAuthenticationService;

    @Mock
    private RefreshTokenService mockRefreshTokenService;

    @InjectMocks
    private JwtAuthenticationController jwtAuthenticationController;

    private GsonTester<JwtResponse> jsonJwtResponse;
    private GsonTester<JwtRequest> jsonJwtRequest;
    private GsonTester<SignUpRequest> jsonSignUpRequest;
    private GsonTester<TokenRefreshRequest> jsonTokenRefreshRequest;
    private GsonTester<LogoutRequest> jsonLogoutRequest;

    @BeforeEach
    public void setup() {
        Gson gson = new GsonBuilder().create();
        GsonTester.initFields(this, gson);

        mockMvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationController)
                .setControllerAdvice(
                        new RefreshTokenNotFoundControllerAdvice(),
                        new TokenControllerAdvice(),
                        new UserNotFoundControllerAdvice()
                )
                .build();
    }

    @Test
    void postValidAuthentication() throws Exception {
        given(mockJwtAuthenticationService.createJWTToken("email", "password"))
                .willReturn("token");
        given(mockRefreshTokenService.createRefreshToken("email"))
                .willReturn("refresh-token");

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/authenticate")
                                .content(asJsonString(JwtRequest.builder().email("email").password("password").build())).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString()).isEqualTo(
                jsonJwtResponse.write(JwtResponse.builder().token("token").refreshToken("refresh-token").build()).getJson()
        );

    }

    private static String asJsonString(final Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}