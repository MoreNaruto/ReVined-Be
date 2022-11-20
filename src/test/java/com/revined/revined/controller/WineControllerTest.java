package com.revined.revined.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.revined.revined.model.Wine;
import com.revined.revined.request.AddWineRequest;
import com.revined.revined.service.WineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static com.revined.revined.util.TestHelper.asJsonString;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WineControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WineService mockWineService;

    @InjectMocks
    private WineController wineController;

    @Captor
    ArgumentCaptor<AddWineRequest> addWineRequestArgumentCaptor;

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
    void testCreateWine() throws Exception {
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


        verify(mockWineService).addWine(addWineRequestArgumentCaptor.capture());
        AddWineRequest captoredWineRequest = addWineRequestArgumentCaptor.getValue();

        assertEquals(request.getAlohaCode(), captoredWineRequest.getAlohaCode());
        assertArrayEquals(request.getAromas(), captoredWineRequest.getAromas());
        assertEquals(request.getBody(), captoredWineRequest.getBody());
        assertEquals(request.getColor(), captoredWineRequest.getColor());
        assertEquals(request.getCountry(), captoredWineRequest.getCountry());
        assertEquals(request.getDescription(), captoredWineRequest.getDescription());
        assertEquals(request.getEffervescence(), captoredWineRequest.getEffervescence());
        assertEquals(request.getFarmingPractices(), captoredWineRequest.getFarmingPractices());
        assertArrayEquals(request.getFoodPairing(), captoredWineRequest.getFoodPairing());
        assertArrayEquals(request.getGrapes(), captoredWineRequest.getGrapes());
        assertEquals(request.getName(), captoredWineRequest.getName());
        assertEquals(request.getPhotoLink(), captoredWineRequest.getPhotoLink());
        assertEquals(request.getProducer(), captoredWineRequest.getProducer());
        assertEquals(request.getRegion(), captoredWineRequest.getRegion());
        assertEquals(request.getSubRegion(), captoredWineRequest.getSubRegion());
        assertEquals(request.getVintage(), captoredWineRequest.getVintage());
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "Successfully created wine");
    }

    @Test
    void testGetAllWines() throws Exception {
        Wine wineOne = Wine.builder()
                .alohaCode("Aloha Code #123")
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

        Wine wineTwo = Wine.builder()
                .alohaCode("Aloha Code #456")
                .aromas(new String[]{"moist"})
                .body("Heavy and delicious")
                .color("Purple")
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

        Mockito.when(mockWineService.getAllWines())
                .thenReturn(List.of(wineOne, wineTwo));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/wine/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "[{\"uuid\":null," +
                "\"name\":\"Fortnier Wine\",\"description\":\"Best wine ever\"," +
                "\"alohaCode\":\"Aloha Code #123\",\"color\":\"Red\",\"producer\":\"Fortnier Folks\"," +
                "\"vintage\":\"2017\",\"grapes\":[\"vinal\"],\"aromas\":[\"nutty\",\"earthy\"]," +
                "\"effervescence\":\"Rosey\",\"country\":\"Italy\",\"region\":\"Sicily\"," +
                "\"subRegion\":\"Forest\",\"farmingPractices\":\"Using a rake\",\"body\":\"Some body\"," +
                "\"photoLink\":\"https://www.photo.com\",\"foodPairing\":[\"Steak\",\"Chicken\"]," +
                "\"inventories\":[],\"createdAt\":null,\"updatedAt\":null},{\"uuid\":null," +
                "\"name\":\"Fortnier Wine\",\"description\":\"Best wine ever\",\"alohaCode\":\"Aloha Code #456\"," +
                "\"color\":\"Purple\",\"producer\":\"Fortnier Folks\",\"vintage\":\"2017\",\"grapes\":[\"vinal\"]," +
                "\"aromas\":[\"moist\"],\"effervescence\":\"Rosey\",\"country\":\"Italy\",\"region\":\"Sicily\"," +
                "\"subRegion\":\"Forest\",\"farmingPractices\":\"Using a rake\",\"body\":\"Heavy and delicious\"," +
                "\"photoLink\":\"https://www.photo.com\",\"foodPairing\":[\"Steak\",\"Chicken\"],\"inventories\":[]," +
                "\"createdAt\":null,\"updatedAt\":null}]"
        );
    }

    @Test
    void testDeleteWine() throws Exception{
        UUID wineId = UUID.randomUUID();

        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/wine/delete/" + wineId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        verify(mockWineService).deleteWine(wineId);

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "Successfully deleted wine");
    }
}