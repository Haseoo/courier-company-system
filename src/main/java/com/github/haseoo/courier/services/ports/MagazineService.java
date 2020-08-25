package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.places.AddressData;
import com.github.haseoo.courier.servicedata.places.MagazineAddOperationData;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.places.MagazineEditOperationData;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Consumer;

public interface MagazineService {
    List<MagazineData> getList();

    List<MagazineData> getActiveList();

    MagazineData getById(Long id);

    MagazineData add(MagazineAddOperationData magazineAddOperationData);

    MagazineData edit(Long id, MagazineEditOperationData magazineEditOperationData);

    MagazineData addLogisitcians(Long magazineId, @NotNull List<Long> logisticiansIds);

    void consumeClosestMagazine(AddressData address, Consumer<MagazineModel> consumer);

    List<ParcelData> getAssignedAtSenderParcels(Long id);

    List<ParcelData> getParcelsToReturn(Long id);
}
