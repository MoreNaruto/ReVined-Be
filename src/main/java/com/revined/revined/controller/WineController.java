package com.revined.revined.controller;

import com.revined.revined.model.Wine;
import com.revined.revined.request.AddWineRequest;
import com.revined.revined.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Wine>> getAllWines() {
        return ResponseEntity.ok(service.getAllWines());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> deleteWine(@PathVariable String id) {
        UUID wineUuid = UUID.fromString(id);
        try {
            service.deleteWine(wineUuid);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Successfully deleted wine");
    }
}
