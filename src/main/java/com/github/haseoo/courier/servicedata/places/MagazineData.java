package com.github.haseoo.courier.servicedata.places;

import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class MagazineData {
    private Long id;
    private AddressData address;
    private Boolean active;
    private List<LogisticianData> logisticians;
}
