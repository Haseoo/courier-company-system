package com.github.haseoo.courier.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Logistician")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "logistcianId")
public class LogisticianModel extends EmployeeModel {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "magazineId")
    private MagazineModel magazine;
}
