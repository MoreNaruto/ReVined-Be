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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "inventories")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;

    @ManyToMany()
    @JoinTable(
            name = "wines_inventories",
            joinColumns = { @JoinColumn(name = "inventory_id")},
            inverseJoinColumns = { @JoinColumn(name = "wine_id")}
    )
    @Builder.Default
    Set<Wine> wines = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;
}
