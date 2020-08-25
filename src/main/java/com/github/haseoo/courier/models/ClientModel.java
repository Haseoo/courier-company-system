package com.github.haseoo.courier.models;

import com.github.haseoo.courier.enums.ClientType;
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
@PrimaryKeyJoinColumn(name = "userId")
public class ClientModel extends UserModel {
    @Column(nullable = false)
    private String emailAddress;
    @Column
    private String phoneNumber;
    @Column(nullable = false)
    private ClientType clientType;

    @OneToMany(mappedBy = "sender", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    List<ParcelModel> sentParcels;
    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(joinColumns = @JoinColumn(name = "clientId"),
            inverseJoinColumns = @JoinColumn(name = "parcelId"))
    List<ParcelModel> observedParcels;
}
