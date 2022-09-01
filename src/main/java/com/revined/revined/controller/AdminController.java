package com.revined.revined.controller;

import com.revined.revined.exception.CompanyNotFoundException;
import com.revined.revined.exception.InvalidRequestTokenHeaderException;
import com.revined.revined.exception.UserNotFoundException;
import com.revined.revined.model.User;
import com.revined.revined.request.AddInventoryRequest;
import com.revined.revined.service.InventoryService;
import com.revined.revined.service.UserService;
import com.revined.revined.utils.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenHandler tokenHandler;

    @RequestMapping(value = "/new/inventory", method = RequestMethod.POST)
    public ResponseEntity<String> createInventory(
            @Valid @RequestBody AddInventoryRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String userToken
    ) throws InvalidRequestTokenHeaderException, UserNotFoundException, CompanyNotFoundException {
        String username = tokenHandler.getUsernameFromJwtToken(userToken);

        User user = userService.getUserByUsername(username);

        inventoryService.createInventory(request.getName(), request.getDescription(), request.getCompanyId(), user);

        return ResponseEntity.ok("Successfully made inventory");
    }
}
