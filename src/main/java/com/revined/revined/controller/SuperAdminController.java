package com.revined.revined.controller;

import com.revined.revined.request.AddCompanyRequest;
import com.revined.revined.request.AddUsersToCompanyRequest;
import com.revined.revined.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/super_admin")
public class SuperAdminController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/new/company", method = RequestMethod.POST)
    public ResponseEntity<String> createCompany(@Valid @RequestBody AddCompanyRequest request) {
        companyService.addCompany(request);

        return ResponseEntity.ok("Successfully created company");
    }

    @RequestMapping(value = "/add/user/company/{companyId}", method = RequestMethod.POST)
    public ResponseEntity<String> addUsersToCompany(
            @PathVariable("companyId") UUID companyId,
            @Valid @RequestBody AddUsersToCompanyRequest request
    ) {
        return ResponseEntity.ok(companyService.addUsersToCompany(request.getUsers(), companyId));
    }
}
