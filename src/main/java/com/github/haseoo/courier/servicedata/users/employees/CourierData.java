package com.github.haseoo.courier.servicedata.users.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@Getter
@SuperBuilder
@ToString
@AllArgsConstructor(access = PRIVATE)
public class CourierData extends EmployeeData {
    /*private List<ParcelData> assignedParcels*/
}
