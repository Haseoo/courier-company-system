package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;

import static com.github.haseoo.courier.utilities.UserUtils.getEmployeeType;
import static com.github.haseoo.courier.utilities.UserUtils.getUserType;


public class LogisticianModelToData extends MapperConverter<LogisticianModel, LogisticianData> {

    @Override
    protected LogisticianData convert(LogisticianModel logisticianModel) {
        return LogisticianData.builder()
                .id(logisticianModel.getId())
                .userName(logisticianModel.getUserName())
                .password(logisticianModel.getPassword())
                .active(logisticianModel.getActive())
                .name(logisticianModel.getName())
                .surname(logisticianModel.getSurname())
                .phoneNumber(logisticianModel.getPhoneNumber())
                .pesel(logisticianModel.getPesel())
                .employeeType(getEmployeeType(logisticianModel))
                .userType(getUserType(logisticianModel))
                .build();
    }
}
