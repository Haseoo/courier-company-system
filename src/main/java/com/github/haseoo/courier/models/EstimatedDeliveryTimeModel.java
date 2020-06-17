package com.github.haseoo.courier.models;

import lombok.Data;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "EstimatedDeliveryTime")
public class EstimatedDeliveryTimeModel {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(insertable = false, nullable = false)
    private Long id;
    @Column(nullable = false)
    private Integer expectedCourierArrivalAfterAdd;
    @Column(nullable = false)
    private Integer expectedCourierArrivalAfterAddToMagazine;
    @Column(nullable = false)
    private Integer timesAtMagazineToReturn;
    @Column(nullable = false)
    private Integer maxMoveDayAfter;
}
