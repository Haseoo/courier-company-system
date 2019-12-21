package com.github.haseoo.courier.models;

import com.github.haseoo.courier.enums.ParcelStateType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.github.haseoo.courier.enums.ParcelStateType.AT_COURIER_FROM_SENDER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "ParcelStateRecord")
public class ParcelStateRecord {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, insertable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "parcelId")
    private ParcelModel parcel;

    @Column(nullable = false)
    private ParcelStateType state;

    @Column(nullable = false)
    private LocalDateTime changeDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "magazineId")
    private MagazineModel magazine;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "courierId")
    private CourierModel courier;

    public static ParcelStateRecord defaultParcelStateRecord() {
        ParcelStateRecord parcelStateRecord = new ParcelStateRecord();
        parcelStateRecord.setChangeDate(LocalDateTime.now());
        parcelStateRecord.setState(AT_COURIER_FROM_SENDER);
        return parcelStateRecord;
    }

}
