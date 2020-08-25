package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;
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
    private AddressModel address;

    @Column(nullable = false)
    private Boolean active;

    @OneToMany(fetch = LAZY, mappedBy = "magazine", cascade = {MERGE, PERSIST, REFRESH})
    private List<LogisticianModel> logisticians;
    @OneToMany(fetch = LAZY, mappedBy = "magazine", cascade = ALL)
    private List<ParcelStateRecord> parcelStates;

}
