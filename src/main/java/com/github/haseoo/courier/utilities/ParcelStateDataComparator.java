package com.github.haseoo.courier.utilities;

import com.github.haseoo.courier.servicedata.parcels.ParcelStateData;

import java.util.Comparator;

public class ParcelStateDataComparator implements Comparator<ParcelStateData> {
    @Override
    public int compare(ParcelStateData o1, ParcelStateData o2) {
        return o1.getChangeDate().compareTo(o2.getChangeDate());
    }
}
