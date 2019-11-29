package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "Magazine")
public class MagazineModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    AddressModel address;

    @Column(nullable = false)
    Boolean active;

    @OneToMany(mappedBy = "magazine")
    List<LogisticianModel> logisticians;

}
