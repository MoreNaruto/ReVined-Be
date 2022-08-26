package com.revined.revined.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revined.revined.advice.RefreshTokenNotFoundControllerAdvice;
import com.revined.revined.advice.TokenControllerAdvice;
import com.revined.revined.advice.UserNotFoundControllerAdvice;
import com.revined.revined.model.enums.Roles;
import com.revined.revined.request.JwtRequest;
import com.revined.revined.request.LogoutRequest;
import com.revined.revined.request.SignUpRequest;
import com.revined.revined.request.TokenRefreshRequest;
import com.revined.revined.response.JwtResponse;
import com.revined.revined.service.JwtAuthenticationService;
import com.revined.revined.service.RefreshTokenService;
import org.junit.jupiter.api.Assertions;
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

import static com.revined.revined.util.TestHelper.asJsonString;
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

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(),
                jsonJwtResponse.write(JwtResponse.builder().token("token").refreshToken("refresh-token").build()).getJson()
        );
    }

    @Test
    void postSignUp() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest
                .builder()
                .email("email")
                .password("password")
                .matchPassword("password")
                .firstName("Bob")
                .lastName("Dole")
                .role(Roles.ADMIN)
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/sign-up")
                                .content(asJsonString(signUpRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(), "Successfully created user");
    }

    @Test
    void postRefreshToken() throws Exception {
        TokenRefreshRequest request = TokenRefreshRequest
                .builder()
                .refreshToken("refresh-token")
                .build();

        JwtResponse jwtResponse = JwtResponse
                .builder()
                .token("token")
                .refreshToken("refresh-token")
                .build();

        given(mockRefreshTokenService.findTokenByRefreshToken("refresh-token"))
                .willReturn("token");

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/refresh-token")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(), jsonJwtResponse.write(jwtResponse).getJson());
    }

    @Test
    void postLogOut() throws Exception {
        LogoutRequest request = LogoutRequest
                .builder()
                .email("email")
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/log-out")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(), "Log out successful!");
    }


}