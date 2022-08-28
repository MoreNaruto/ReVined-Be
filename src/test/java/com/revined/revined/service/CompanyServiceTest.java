package com.revined.revined.service;

import com.revined.revined.model.Company;
import com.revined.revined.model.enums.Status;
import com.revined.revined.repository.CompanyRepository;
import com.revined.revined.request.AddCompanyRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @Mock
    private CompanyRepository mockCompanyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void addCompany() {
        AddCompanyRequest addCompanyRequest = AddCompanyRequest
                .builder()
                .name("Bob Dole's Company")
                .description("We sell chicken")
                .build();

        companyService.addCompany(addCompanyRequest);

        Company expectedCompany = Company
                .builder()
                .name("Bob Dole's Company")
                .description("We sell chicken")
                .status(Status.ACTIVE)
                .build();

        verify(mockCompanyRepository).save(expectedCompany);
    }
}