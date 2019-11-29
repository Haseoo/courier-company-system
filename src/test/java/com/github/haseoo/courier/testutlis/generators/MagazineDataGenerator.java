package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.MagazineModel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MagazineDataGenerator {
    public static MagazineModel getMagazineModel() {
        MagazineModel magazineModel = new MagazineModel();
        magazineModel.setActive(true);
        magazineModel.setAddress(getAddressModel());
        return magazineModel;
    }

    public static MagazineModel getInactiveMagazineModel() {
        MagazineModel magazineModel = new MagazineModel();
        magazineModel.setActive(false);
        magazineModel.setAddress(getAddressModel());
        return magazineModel;
    }
}
