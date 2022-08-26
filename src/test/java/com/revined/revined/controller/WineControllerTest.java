package com.revined.revined.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revined.revined.request.AddWineRequest;
import com.revined.revined.service.WineService;
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

@ExtendWith(MockitoExtension.class)
class WineControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WineService mockWineService;

    @InjectMocks
    private WineController wineController;

    private GsonTester<AddWineRequest> addWineRequest;

    @BeforeEach
    void setUp() {
        Gson gson = new GsonBuilder().create();
        GsonTester.initFields(this, gson);

        mockMvc = MockMvcBuilders.standaloneSetup(wineController)
                .setControllerAdvice()
                .build();
    }

    @Test
    void createWine() throws Exception {
        AddWineRequest request = AddWineRequest.builder()
                .alohaCode("Aloha Code")
                .aromas(new String[]{"nutty", "earthy"})
                .body("Some body")
                .color("Red")
                .country("Italy")
                .description("Best wine ever")
                .effervescence("Rosey")
                .farmingPractices("Using a rake")
                .foodPairing(new String[]{"Steak", "Chicken"})
                .name("Fortnier Wine")
                .grapes(new String[]{"vinal"})
                .photoLink("https://www.photo.com")
                .producer("Fortnier Folks")
                .region("Sicily")
                .subRegion("Forest")
                .vintage("2017")
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/wine/add")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assertions.assertEquals(response.getContentAsString(), "Successfully created wine");
    }
}