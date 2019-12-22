package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.exceptions.BusinessLogicException;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.*;

public class IllegalParcelState extends BusinessLogicException {
    public IllegalParcelState() {
        super(ILLEGAL_PARCEL_STATE);
    }

    public IllegalParcelState(Long id, ParcelStateType currentState, ParcelStateType[] acceptableStates) {
        super(parcelStateType(id, currentState, acceptableStates));
    }

    private static String parcelStateType(Long id, ParcelStateType currentState, ParcelStateType[] acceptableStates) {
        return Arrays.stream(acceptableStates)
                .map(state -> String.format(ACCEPTABLE_STATES_LIST_FORMAT, state))
                .collect(Collectors.joining("", String.format(ILLEGAL_PARCEL_STATE_EXCEPTION_FORMAT, id, currentState), ""));
    }
}
