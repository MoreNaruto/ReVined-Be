package com.revined.revined.service;

import com.revined.revined.model.Wine;
import com.revined.revined.repository.WineRepository;
import com.revined.revined.request.AddWineRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WineServiceTest {

    @Mock
    private WineRepository mockWineRepository;

    @InjectMocks
    private WineService wineService;

    @Test
    void testAddWine() {
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

        Wine expectedWine = Wine
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .alohaCode(request.getAlohaCode())
                .color(request.getColor())
                .producer(request.getProducer())
                .vintage(request.getVintage())
                .grapes(request.getGrapes())
                .aromas(request.getAromas())
                .effervescence(request.getEffervescence())
                .country(request.getCountry())
                .region(request.getRegion())
                .subRegion(request.getSubRegion())
                .farmingPractices(request.getFarmingPractices())
                .body(request.getBody())
                .photoLink(request.getPhotoLink())
                .foodPairing(request.getFoodPairing())
                .build();

        wineService.addWine(request);

        verify(mockWineRepository).save(expectedWine);
    }

    @Test
    void testGetAllWines() {
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

        when(mockWineRepository.findAll())
                .thenReturn(List.of(wineOne, wineTwo));

        List<Wine> allWines = wineService.getAllWines();

        Assertions.assertEquals(2, allWines.size());
        Assertions.assertEquals(wineOne, allWines.get(0));
        Assertions.assertEquals(wineTwo, allWines.get(1));
    }

    @Test
    void testDeleteWine() {
        UUID wineId = UUID.randomUUID();

        wineService.deleteWine(wineId);

        verify(mockWineRepository).deleteById(wineId);
    }
}