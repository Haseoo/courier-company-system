package com.github.haseoo.courier.servicedata.users.employees;

import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode(callSuper = true)
@Value
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
public class CourierData extends EmployeeData {
    /*private List<ParcelData> assignedParcels*/

    public static CourierData of(CourierModel courierModel) {
        return CourierData.builder()
                .id(courierModel.getId())
                .userName(courierModel.getUserName())
                .password(courierModel.getPassword())
                .active(courierModel.getActive())
                .name(courierModel.getName())
                .surname(courierModel.getSurname())
                .phoneNumber(courierModel.getPhoneNumber())
                .pesel(courierModel.getPesel())
                .employeeType(UserUtils.getEmployeeType(courierModel))
                .userType(UserUtils.getUserType(courierModel))
                .build();
    }
}
