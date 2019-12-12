package com.github.haseoo.courier.models;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "courier")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "employeeId")
public class CourierModel extends EmployeeModel {
    @OneToMany(fetch = LAZY, mappedBy = "courier", cascade = ALL)
    private List<ParcelStateRecord> parcelStates;
}
