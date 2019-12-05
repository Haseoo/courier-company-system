package com.github.haseoo.courier.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "logistician")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "logistcianId")
public class LogisticianModel extends EmployeeModel {
    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "magazineId")
    private MagazineModel magazine;
}
