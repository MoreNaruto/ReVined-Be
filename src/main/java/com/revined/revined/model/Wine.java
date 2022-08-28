package com.revined.revined.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "wines")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        ),
        @TypeDef(
                name = "int-array",
                typeClass = IntArrayType.class
        )
})
public class Wine {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "aloha_code")
    private String alohaCode;

    @Column(name = "color")
    private String color;

    @Column(name = "producer")
    private String producer;

    @Column(name = "vintage")
    private String vintage;

    @Column(name = "grapes",
            columnDefinition = "text[]"
    )
    private String[] grapes;

    @Column(name = "aromas",
            columnDefinition = "text[]"
    )
    private String[] aromas;

    @Column(name = "effervescence")
    private String effervescence;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "sub_region")
    private String subRegion;

    @Column(name = "farming_practices")
    private String farmingPractices;

    @Column(name = "body")
    private String body;

    @Column(name = "photo_link")
    private String photoLink;

    @Column(name = "food_pairing",
            columnDefinition = "text[]"
    )
    private String[] foodPairing;

    @ManyToMany(mappedBy = "wines")
    @Builder.Default
    private Set<Inventory> inventories = new HashSet<>();

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;
}
