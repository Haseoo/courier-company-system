package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.places.MagazineData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;

import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressData;
import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelInMagazine;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getLogisticianModel;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MagazineDataGenerator {

    public static MagazineModel getMagazineModel() {
        MagazineModel magazineModel = new MagazineModel();
        magazineModel.setActive(true);
        magazineModel.setAddress(getAddressModel());
        magazineModel.setParcelStates(new ArrayList<>());
        magazineModel.setLogisticians(new ArrayList<>());
        return magazineModel;
    }

    public static MagazineModel getInactiveMagazineModel() {
        MagazineModel magazineModel = new MagazineModel();
        magazineModel.setActive(false);
        magazineModel.setAddress(getAddressModel());
        magazineModel.setParcelStates(new ArrayList<>());
        magazineModel.setParcelStates(new ArrayList<>());
        return magazineModel;
    }

    public static MagazineData getMagazineData() {
        return MagazineData.builder()
                .id(1L)
                .address(getAddressData())
                .active(true)
                .logisticians(Collections.singletonList(LogisticianData.of(getLogisticianModel())))
                .parcels(Collections.singletonList(ParcelData.of(getParcelInMagazine())))
                .build();

    }
}
