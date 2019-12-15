package com.github.haseoo.courier.configuration.converters;

import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import com.github.haseoo.courier.views.users.employees.LogisticianView;

public class LogisticianDataToView extends MapperConverter<LogisticianData, LogisticianView> {
    @Override
    protected LogisticianView convert(LogisticianData logisticianData) {
        return LogisticianView
                .builder()
                    .id(logisticianData.getId())
                    .name(logisticianData.getName())
                    .surname(logisticianData.getSurname())
                    .pesel(logisticianData.getPesel())
                    .phoneNumber(logisticianData.getPesel())
                .build();
    }
}
