package com.revined.revined.repository;


import com.revined.revined.model.RefreshToken;
import com.revined.revined.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserUuid(UUID userId);

    @Modifying
    void deleteByUser(User user);
}
