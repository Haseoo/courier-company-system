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
@Table(name = "ReceiverInfo")
public class ReceiverInfoModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Long id;
    @Column(nullable = false, columnDefinition="TEXT")
    private String name;
    @Column(nullable = false, columnDefinition="TEXT")
    private String surname;
    private String emailAddress;
    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "receiverContactData", fetch = LAZY)
    List<ParcelModel> parcels;
}
