package com.github.haseoo.courier.views.users.employees;

import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LogisticianWithoutMagazineView {
    private Long id;
    private String pesel;
    private String name;
    private String surname;
    private String phoneNumber;

    public static LogisticianWithoutMagazineView of(LogisticianData logisticianData) {
        return LogisticianWithoutMagazineView
                .builder()
                .id(logisticianData.getId())
                .name(logisticianData.getName())
                .surname(logisticianData.getSurname())
                .pesel(logisticianData.getPesel())
                .phoneNumber(logisticianData.getPesel())
                .build();
    }
}
