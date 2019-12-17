package com.github.haseoo.courier.views.users.employees;

import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import com.github.haseoo.courier.views.places.MagazineWoLogisticiansView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LogisticianView {
    private Long id;
    private String pesel;
    private String name;
    private String surname;
    private String phoneNumber;
    private MagazineWoLogisticiansView magazine;

    public static LogisticianView of(LogisticianData logisticianData) {
        return LogisticianView
                .builder()
                .id(logisticianData.getId())
                .name(logisticianData.getName())
                .surname(logisticianData.getSurname())
                .pesel(logisticianData.getPesel())
                .phoneNumber(logisticianData.getPesel())
                .magazine(((logisticianData.getMagazine() != null) ? MagazineWoLogisticiansView.of(logisticianData.getMagazine()) : null))
                .build();
    }
}
