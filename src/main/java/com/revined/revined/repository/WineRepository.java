package com.revined.revined.repository;

import com.revined.revined.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WineRepository extends JpaRepository<Wine, UUID> {
    boolean existsByName(String name);
}
