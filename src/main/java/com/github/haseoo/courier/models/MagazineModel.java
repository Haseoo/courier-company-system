package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "Magazine")
public class MagazineModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = ALL)
    AddressModel address;

    @Column(nullable = false)
    Boolean active;

    @OneToMany(mappedBy = "magazine", cascade = ALL)
    List<LogisticianModel> logisticians;
    @OneToMany(fetch = LAZY, mappedBy = "magazine", cascade = ALL)
    private List<ParcelStateRecord> parcelStates;

}
