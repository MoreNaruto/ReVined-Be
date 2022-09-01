package com.revined.revined.controller;

import com.revined.revined.request.AddWineRequest;
import com.revined.revined.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/wine")
public class WineController {
    @Autowired
    private WineService service;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> createWine(@Valid @RequestBody AddWineRequest request) {
        service.addWine(request);

        return ResponseEntity.ok("Successfully created wine");
    }
}
