package com.github.haseoo.courier.servicedata.places;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class AddressData extends AddressOperationData {
    private Long id;
}
