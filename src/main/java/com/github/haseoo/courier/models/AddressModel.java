package com.github.haseoo.courier.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@EqualsAndHashCode
@Mapper
@Table(name = "address")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String buildingNumber;

    private String flatNumber;
}
