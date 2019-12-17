package com.github.haseoo.courier.servicedata.users.employees;

import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.servicedata.places.AddressData;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@Getter
@SuperBuilder
@AllArgsConstructor(access = PRIVATE)
public class LogisticianData extends EmployeeData {
    private MagazineData magazine;

    public static LogisticianData of(LogisticianModel logisticianModel) {
        return LogisticianData.builder()
                .id(logisticianModel.getId())
                .userName(logisticianModel.getUserName())
                .password(logisticianModel.getPassword())
                .active(logisticianModel.getActive())
                .name(logisticianModel.getName())
                .surname(logisticianModel.getSurname())
                .phoneNumber(logisticianModel.getPhoneNumber())
                .pesel(logisticianModel.getPesel())
                .employeeType(UserUtils.getEmployeeType(logisticianModel))
                .userType(UserUtils.getUserType(logisticianModel))
                .magazine(((logisticianModel.getMagazine() != null)
                        ? MagazineData
                        .builder()
                        .active(logisticianModel.getMagazine().getActive())
                        .address(AddressData.of(logisticianModel.getMagazine().getAddress()))
                        .id(logisticianModel.getMagazine().getId())
                        .build()
                        : null)
                )
                .build();
    }
}
