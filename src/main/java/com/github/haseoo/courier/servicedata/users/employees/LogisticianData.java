package com.github.haseoo.courier.servicedata.users.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@Getter
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
public class LogisticianData extends EmployeeData {
    //private MagazineData magazine
}
