package com.github.haseoo.courier.views.users.employees;

import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class CourierView {
    private Long id;
    private String pesel;
    private String name;
    private String surname;
    private String phoneNumber;
    //parcels ids

    public static CourierView of(CourierData courierData) {
        return CourierView
                .builder()
                    .id(courierData.getId())
                    .name(courierData.getName())
                    .surname(courierData.getSurname())
                    .pesel(courierData.getPesel())
                    .phoneNumber(courierData.getPhoneNumber())
                .build();
    }
}
