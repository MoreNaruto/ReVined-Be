package com.revined.revined.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "company")
    private Set<User> users;

    @OneToMany(mappedBy = "company")
    private Set<Inventory> inventories;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;
}
