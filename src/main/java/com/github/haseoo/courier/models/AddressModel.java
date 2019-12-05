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
    @Column(nullable = false, columnDefinition="TEXT")
    private String city;
    @Column(nullable = false, columnDefinition="TEXT")
    private String street;
    @Column(nullable = false, columnDefinition="TEXT")
    private String postalCode;
    @Column(nullable = false, columnDefinition="TEXT")
    private String buildingNumber;
    @Column(columnDefinition="TEXT")
    private String flatNumber;

    @OneToMany(mappedBy = "senderAddress", fetch = LAZY)
    List<ParcelModel> senderAddressParcels;
    @OneToMany(mappedBy = "deliveryAddress", fetch = LAZY)
    List<ParcelModel> deliveryAddressParcels;
}
