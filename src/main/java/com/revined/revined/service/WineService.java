package com.revined.revined.service;

import com.revined.revined.model.Wine;
import com.revined.revined.repository.WineRepository;
import com.revined.revined.request.AddWineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WineService {
    @Autowired
    private WineRepository wineRepository;

    public void addWine(AddWineRequest request) {
        Wine wine = Wine
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

        wineRepository.save(wine);
    }
}
