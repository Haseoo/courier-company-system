package com.github.haseoo.courier.views.users.employees;

import com.github.haseoo.courier.views.places.MagazineWoLogisticianView;
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
    private MagazineWoLogisticianView magazine;
}
