package com.github.haseoo.courier.views.places;

import com.github.haseoo.courier.enums.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@AllArgsConstructor(access = PRIVATE)
public class PlaceView {
    private PlaceType placeType;
    private AddressView address;
}
