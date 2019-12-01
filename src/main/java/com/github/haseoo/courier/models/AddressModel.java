package com.github.haseoo.courier.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "Address")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String buildingNumber;
    private String flatNumber;

    @OneToMany(mappedBy = "senderAddress", fetch = LAZY)
    List<ParcelModel> senderAddressParcels;
    @OneToMany(mappedBy = "deliveryAddress", fetch = LAZY)
    List<ParcelModel> deliveryAddressParcels;
}
