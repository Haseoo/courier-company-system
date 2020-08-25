package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "Parcel")
public class ParcelModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, insertable = false)
    private Long id;
    @Column(nullable = false)
    private char[] pin;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "parcelTypeId")
    private ParcelTypeModel parcelType;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "deliveryAddressId")
    private AddressModel deliveryAddress;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "senderAddressId")
    private AddressModel senderAddress;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "clientId")
    private ClientModel sender;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "receiverContactDataId")
    private ReceiverInfoModel receiverContactData;
    private LocalDate expectedCourierArrivalDate;
    @Column(nullable = false)
    private Boolean priority;
    @Column(nullable = false)
    private BigDecimal parcelFee;
    @Column(nullable = false, name = "isPaid")
    private Boolean paid;
    @Column(nullable = false, name = "isDateMoved")
    private Boolean dateMoved;
    @Column(nullable = false, name = "isToReturn")
    private Boolean toReturn = false;

    @OneToMany(fetch = LAZY, mappedBy = "parcel", cascade = ALL)
    private List<ParcelStateRecord> parcelStates;
    @ManyToMany(fetch = LAZY, mappedBy = "observedParcels")
    List<ClientModel> observingClients;
}
