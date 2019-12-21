package com.github.haseoo.courier.utilities;

import com.github.haseoo.courier.models.ParcelStateRecord;

import java.util.Comparator;

public class ParcelStateRecordComparator implements Comparator<ParcelStateRecord> {
    @Override
    public int compare(ParcelStateRecord o1, ParcelStateRecord o2) {
        return o1.getChangeDate().compareTo(o2.getChangeDate());
    }
}
