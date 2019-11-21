package com.github.haseoo.courier.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
//@Data
@EqualsAndHashCode
@Mapper
@Table(name = "address")
@Builder
public class AddressModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Integer addressId;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String houseNumber;
    private String apartmentNumber;
    @Column(nullable = false)
    private String postalCode;

    @OneToMany(mappedBy = "receiverAddress", fetch = FetchType.EAGER)
    private List<PackageModel> receivePackages;
    @OneToMany(mappedBy = "senderAddress", fetch = FetchType.EAGER)
    private List<PackageModel> sendPackages;
}
