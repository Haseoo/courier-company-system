package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;

import java.time.LocalDate;
import java.util.List;

public interface ParcelService {
    ParcelData add(ParcelAddData parcelAddData);

    ParcelData edit(Long id, ParcelEditData parcelEditData);

    ParcelData setParcelToReturn(Long id);

    List<ParcelData> getList();

    ParcelData getById(Long id);

    void delete(Long id);

    ParcelData moveDate(Long id, char[] pin, LocalDate newDate);
}
