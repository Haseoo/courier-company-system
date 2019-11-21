package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.models.PackageModel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.courier.testutlis.Constants.PackageDataConstants.ADDITIONAL_INFO;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PackageDataGenerator {
    public static PackageModel getPackageModel() {
        PackageModel packageModel = new PackageModel();
        packageModel.setAdditionalInfo(ADDITIONAL_INFO);
        packageModel.setPackageId(null);
        packageModel.setReceiverAddress(AddressModel.builder().build());
        packageModel.setReceiverAddress(AddressModel.builder().build());
        return packageModel;
    }
}
