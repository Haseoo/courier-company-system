package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "packages")
@Data
public class PackageModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Integer packageId;
    @Column(nullable = false)
    private String additionalInfo;
    @ManyToOne
    @JoinColumn(name = "receiverAddressId", referencedColumnName = "addressId", nullable = false)
    private AddressModel receiverAddress;
    @ManyToOne
    @JoinColumn(name = "senderAddressId", referencedColumnName = "addressId", nullable = false)
    private AddressModel senderAddress;
}
