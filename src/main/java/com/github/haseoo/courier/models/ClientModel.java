package com.github.haseoo.courier.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Client")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "clientId")
public class ClientModel extends UserModel {
    private String emailAddress;
    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "sender", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    List<ParcelModel> sentParcels;
    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(joinColumns = @JoinColumn(name = "clientId"),
            inverseJoinColumns = @JoinColumn(name = "parcelId"))
    List<ParcelModel> observedParcels;
}
