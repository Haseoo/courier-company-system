package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.querydata.AddressQueryData;
import com.github.haseoo.courier.repositories.ports.AddressRepository;
import com.github.haseoo.courier.servicedata.places.AddressOperationData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class AddressServiceImplTest {
    @Mock
    private AddressRepository addressRepository;

    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private AddressServiceImpl sut;

    @Test
    void should_consume_existing_address() {
        //given
        Consumer<AddressModel> consumer = e -> {
        };
        AddressOperationData addressOperationData = AddressOperationData.of(getAddressModel());
        when(addressRepository.addressExist(AddressQueryData.of(addressOperationData))).thenReturn(Optional.of(getAddressModel()));
        //when
        sut.consume(addressOperationData, consumer);
        //then
        verify(addressRepository, never()).saveAndFlush(any());
    }

    @Test
    void should_consume_new_address() {
        //given
        Consumer<AddressModel> consumer = e -> {
        };
        AddressOperationData addressOperationData = AddressOperationData.builder()
                .buildingNumber("1")
                .city("city")
                .flatNumber("1")
                .postalCode("11-111")
                .street("street")
                .build();
        when(addressRepository.addressExist(AddressQueryData.of(addressOperationData))).thenReturn(Optional.empty());
        ArgumentCaptor<AddressModel> argument = ArgumentCaptor.forClass(AddressModel.class);
        //when
        sut.consume(addressOperationData, consumer);
        //then
        verify(addressRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getCity()).isEqualTo(addressOperationData.getCity());
        Assertions.assertThat(argument.getValue().getBuildingNumber()).isEqualTo(addressOperationData.getBuildingNumber());
        Assertions.assertThat(argument.getValue().getFlatNumber()).isEqualTo(addressOperationData.getFlatNumber());
        Assertions.assertThat(argument.getValue().getStreet()).isEqualTo(addressOperationData.getStreet());
        Assertions.assertThat(argument.getValue().getPostalCode()).isEqualTo(addressOperationData.getPostalCode());


    }

    @Test
    void should_return_list_with_one_element() {
        //given
        List<AddressModel> addressModelList = Arrays.asList(getAddressModel());
        when(addressRepository.getList()).thenReturn(addressModelList);
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(1);
    }
}