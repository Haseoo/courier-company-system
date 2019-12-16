package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.places.MagazineAddOperationData;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.places.MagazineEditOperationData;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MagazineService {
    List<MagazineData> getList();

    List<MagazineData> getActiveList();

    MagazineData getById(Long id);

    MagazineData add(MagazineAddOperationData magazineAddOperationData);

    MagazineData edit(Long id, MagazineEditOperationData magazineEditOperationData);

    MagazineData addLogisitcians(Long magazineId, @NotNull List<Long> logisticiansIds);
}
