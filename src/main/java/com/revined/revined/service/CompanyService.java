package com.revined.revined.service;

import com.revined.revined.exception.CompanyNotFoundException;
import com.revined.revined.exception.UserNotFoundException;
import com.revined.revined.model.Company;
import com.revined.revined.model.User;
import com.revined.revined.model.enums.Status;
import com.revined.revined.repository.CompanyRepository;
import com.revined.revined.repository.UserRepository;
import com.revined.revined.request.AddCompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public void addCompany(AddCompanyRequest request) {
        Company company = Company
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(Status.ACTIVE)
                .build();

        companyRepository.save(company);
    }

    @Transactional
    public String addUsersToCompany(@NotEmpty(message = "Need to provide user ids") UUID[] users, UUID companyId) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new CompanyNotFoundException("Provided company id is not valid"));

            stringBuilder.append("Company ").append(company.getName()).append(" added users: ");

            Arrays.stream(users).forEach(userId -> {
                try {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("One of the user ids are not valid"));
                    user.setCompany(company);
                    userRepository.save(user);
                    stringBuilder.append(" ").append(user.getEmail());
                } catch (UserNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (CompanyNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}
