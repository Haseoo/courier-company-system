package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.*;
import com.github.haseoo.courier.repositories.jpa.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getActiveParcelTypeModel;
import static com.github.haseoo.courier.testutlis.generators.ReceiverInfoDataGenerator.getReceiverInfoModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
public abstract class TestsWithParcels {
    @Autowired
    protected ParcelJPARepository parcelJPARepository;
    @Autowired
    private ParcelTypeJPARepository parcelTypeJPARepository;
    @Autowired
    private ClientCompanyJPARepository clientCompanyJPARepository;
    @Autowired
    private AddressJPARepository addressJPARepository;
    @Autowired
    private ReceiverInfoJPARepository receiverInfoJPARepository;

    private ClientCompanyModel client;
    private ParcelTypeModel type;
    private AddressModel addressModel;
    private ReceiverInfoModel receiverInfoModel;
    protected ParcelModel parcelModel;

    @BeforeEach
    void setup() {
        parcelJPARepository.deleteAll();
        parcelTypeJPARepository.deleteAll();
        clientCompanyJPARepository.deleteAll();
        addressJPARepository.deleteAll();
        receiverInfoJPARepository.deleteAll();
        addressModel = addressJPARepository.saveAndFlush(getAddressModel());
        type = parcelTypeJPARepository.saveAndFlush(getActiveParcelTypeModel());
        client = clientCompanyJPARepository.saveAndFlush(getCompanyClientModel());
        receiverInfoModel = receiverInfoJPARepository.saveAndFlush(getReceiverInfoModel());
        parcelModel = parcelJPARepository.saveAndFlush(getParcelModel(type, client, addressModel, receiverInfoModel));
    }

    @AfterEach
    void tearDown() {
        parcelJPARepository.deleteAll();
        parcelTypeJPARepository.deleteAll();
        clientCompanyJPARepository.deleteAll();
        addressJPARepository.deleteAll();
        receiverInfoJPARepository.deleteAll();
    }
}
