package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;

import java.util.List;

public interface ParcelStateService {
    MagazineData addParcelsToMagazine(Long magazineId, List<Long> parcelIds);

    CourierData assignParcelsToCourier(Long courierId, List<Long> parcelIds);

    CourierData setAsPickedByCourier(Long courierId, Long parcelId, boolean wasPaid);

    ParcelData setParcelAsDelivered(Long courierId, Long parcelId, boolean wasPaid);

    ParcelData setParcelReturned(Long courierId, Long parcelId, boolean wasPaid);
}
