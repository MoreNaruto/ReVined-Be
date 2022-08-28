package com.revined.revined.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revined.revined.request.AddCompanyRequest;
import com.revined.revined.service.CompanyService;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SuperAdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompanyService mockCompanyService;

    @InjectMocks
    private SuperAdminController superAdminController;

    private GsonTester<AddCompanyRequest> addCompanyRequest;

    @BeforeEach
    void setUp() {
        Gson gson = new GsonBuilder().create();
        GsonTester.initFields(this, gson);

        mockMvc = MockMvcBuilders.standaloneSetup(superAdminController)
                .setControllerAdvice()
                .build();
    }

    @Test
    void createCompany() throws Exception{
        AddCompanyRequest request = AddCompanyRequest
                .builder()
                .name("Bob Dole's Company")
                .description("We sell chicken")
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/super_admin/new/company")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(), "Successfully created company");
    }
}