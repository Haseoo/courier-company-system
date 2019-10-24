package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.PackageModel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.Constants.PackageDataGeneratorConstants.ADDITIONAL_INFO;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PackageDataGenerator {
    public static PackageModel getPackageModel() {
        PackageModel packageModel = new PackageModel();
        packageModel.setAdditionalInfo(ADDITIONAL_INFO);
        return packageModel;
    }
}
