package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.MagazineModel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
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
}
