package com.revined.revined.service;

import com.revined.revined.exception.CompanyNotFoundException;
import com.revined.revined.model.Company;
import com.revined.revined.model.Inventory;
import com.revined.revined.model.User;
import com.revined.revined.repository.CompanyRepository;
import com.revined.revined.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private InventoryRepository inventoryRepository;


    public void createInventory(String name, String description, UUID companyId, User user) throws CompanyNotFoundException {
        Company company = companyRepository.findById(user.getCompany() == null ? companyId : user.getCompany().getUuid())
                .orElseThrow(() -> new CompanyNotFoundException("Can't find company"));

        Inventory inventory = Inventory
                .builder()
                .name(name)
                .description(description)
                .company(company)
                .active(true)
                .build();

        inventoryRepository.save(inventory);
    }
}
