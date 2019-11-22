package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "ParcelType")
public class ParcelTypeModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, insertable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, name = "isActive")
    private Boolean active;
}
