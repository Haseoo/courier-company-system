package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.MagazineDoesNotExist;
import com.github.haseoo.courier.models.EstimatedDeliveryTimeModel;
import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.models.ParcelModel;
import com.github.haseoo.courier.models.ParcelStateRecord;
import com.github.haseoo.courier.repositories.ports.EstimatedDeliveryTimeRepository;
import com.github.haseoo.courier.repositories.ports.MagazineRepository;
import com.github.haseoo.courier.servicedata.places.AddressData;
import com.github.haseoo.courier.servicedata.places.AddressOperationData;
import com.github.haseoo.courier.servicedata.places.MagazineAddOperationData;
import com.github.haseoo.courier.servicedata.places.MagazineEditOperationData;
import com.github.haseoo.courier.services.ports.AddressService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import com.github.haseoo.courier.services.ports.PostalCodeHelper;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
import static com.github.haseoo.courier.testutlis.generators.MagazineDataGenerator.getMagazineModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.*;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getActiveParcelTypeModel;
import static com.github.haseoo.courier.testutlis.generators.ReceiverInfoDataGenerator.getReceiverInfoModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class MagazineServiceImplTest {
    @Mock
    private MagazineRepository magazineRepository;
    @Mock
    private LogisticianService logisticianService;
    @Mock
    private AddressService addressService;
    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();
    @Mock
    private PostalCodeHelper postalCodeHelper;
    @Mock
    private EstimatedDeliveryTimeRepository estimatedDeliveryTimeRepository;


    @InjectMocks
    private MagazineServiceImpl sut;

    @Test
    void should_return_full_list() {
        //given
        List<MagazineModel> magazines = Arrays.asList(getMagazineModel());
        when(magazineRepository.getList()).thenReturn(magazines);
        //when
        sut.getList();
        //then
        verify(magazineRepository).getList();
        verify(magazineRepository, never()).getActiveMagazines();
    }

    @Test
    void should_only_active_full_list() {
        //given
        List<MagazineModel> magazines = Arrays.asList(getMagazineModel());
        when(magazineRepository.getActiveMagazines()).thenReturn(magazines);
        //when
        sut.getActiveList();
        //then
        verify(magazineRepository).getActiveMagazines();
        verify(magazineRepository, never()).getList();
    }

    @Test
    void should_query_by_id() {
        //given
        final long id = 1L;
        when(magazineRepository.getById(anyLong())).thenReturn(Optional.of(getMagazineModel()));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.getById(id);
        verify(magazineRepository).getById(argument.capture());
        //then
        Assertions.assertThat(argument.getValue()).isEqualTo(id);
    }

    @Test
    void should_throw_exception_when_magazine_not_exists() {
        //given
        when(magazineRepository.getById(anyLong())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(1L)).isExactlyInstanceOf(MagazineDoesNotExist.class);
    }

    @Test
    void should_add_magazine() {
        //given
        MagazineAddOperationData in = MagazineAddOperationData.builder()
                .address(AddressOperationData.of(getAddressModel()))
                .build();
        when(magazineRepository.saveAndFlush(any())).thenReturn(getMagazineModel());
        ArgumentCaptor<MagazineModel> argument = ArgumentCaptor.forClass(MagazineModel.class);
        //when
        sut.add(in);
        verify(magazineRepository).saveAndFlush(argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getActive()).isTrue();
        verify(addressService).consume(eq(in.getAddress()), any());
    }

    @Test
    void should_edit_magazine() {
        //given
        final long id = 1L;
        MagazineEditOperationData in = MagazineEditOperationData.builder()
                .active(false)
                .build();
        MagazineModel magazineModel = getMagazineModel();
        when(magazineRepository.getById(anyLong())).thenReturn(Optional.of(magazineModel));
        when(magazineRepository.saveAndFlush(any())).thenReturn(getMagazineModel());
        ArgumentCaptor<MagazineModel> argument = ArgumentCaptor.forClass(MagazineModel.class);
        ArgumentCaptor<Long> idArgument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.edit(id, in);
        verify(magazineRepository).getById(idArgument.capture());
        verify(magazineRepository).saveAndFlush(argument.capture());
        //then
        Assertions.assertThat(idArgument.getValue()).isEqualTo(id);
        Assertions.assertThat(argument.getValue().getActive()).isFalse();
    }

    @Test
    void should_throw_exception_when_magazine_not_exists_edit() {
        //given
        MagazineEditOperationData in = MagazineEditOperationData.builder()
                .active(false)
                .build();
        when(magazineRepository.getById(anyLong())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(1L, in)).isExactlyInstanceOf(MagazineDoesNotExist.class);
    }

    @Test
    void should_add_logisticians() {
        //given
        final long id = 1L;
        final List<Long> lids = Arrays.asList(1L, 2L, 3L);
        MagazineModel magazineModel = getMagazineModel();
        magazineModel.setId(id);
        when(magazineRepository.getById(anyLong())).thenReturn(Optional.of(magazineModel));
        when(magazineRepository.saveAndFlush(any())).thenReturn(magazineModel);
        //when
        sut.addLogisitcians(id, lids);
        //then
        verify(magazineRepository).getById(eq(id));
        verify(logisticianService).consumeAllById(eq(lids), any());
    }

    @Test
    void should_trow_exception_when_adding_logisticians_to_non_existent_magazine() {
        //given
        when(magazineRepository.getById(anyLong())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.addLogisitcians(1L, new ArrayList<>())).isExactlyInstanceOf(MagazineDoesNotExist.class);
    }

    @Test
    void should_throw_execution_when_there_is_no_magazine_to_consume() throws IOException {
        //given
        when(magazineRepository.getActiveMagazines()).thenReturn(new ArrayList<>());
        when(postalCodeHelper.isPostalCodeInCity(anyString(), anyString())).thenReturn(true);
        //then
        Assertions
                .assertThatThrownBy(() -> sut.consumeClosestMagazine(AddressData.of(getAddressModel()), e -> {
                }))
                .isExactlyInstanceOf(MagazineDoesNotExist.class);
    }

    @Test
    void should_return_parcels_at_senders() {
        //given
        MagazineModel magazine = getMagazineModel();
        magazine.setParcelStates(new ArrayList<>());
        ParcelModel parcelModel = getParcelModel(getActiveParcelTypeModel(),
                getCompanyClientModel(),
                getAddressModel(),
                getReceiverInfoModel());
        parcelModel.setId(1L);
        parcelModel.setParcelStates(new ArrayList<>());
        ParcelStateRecord parcelStateRecord = getTestMagazineRecordModel(parcelModel, magazine);
        parcelModel.getParcelStates().add(parcelStateRecord);
        magazine.getParcelStates().add(parcelStateRecord);
        when(magazineRepository.getById(anyLong())).thenReturn(Optional.of(magazine));
        //when & then
        Assertions.assertThat(sut.getAssignedAtSenderParcels(1L).size()).isOne();
    }

    @Test
    void should_return_parcels_to_return() {
        MagazineModel magazine = getMagazineModel();
        magazine.setParcelStates(new ArrayList<>());
        ParcelModel parcelModel = getParcelModel(getActiveParcelTypeModel(),
                getCompanyClientModel(),
                getAddressModel(),
                getReceiverInfoModel());
        parcelModel.setId(1L);
        parcelModel.setParcelStates(new ArrayList<>());
        ParcelStateRecord parcelStateRecord = getTestMagazineRecordModelIn(parcelModel, magazine);
        parcelModel.getParcelStates().add(parcelStateRecord);
        parcelModel.getParcelStates().add(parcelStateRecord);
        parcelModel.getParcelStates().add(parcelStateRecord);
        parcelModel.getParcelStates().add(parcelStateRecord);
        parcelModel.getParcelStates().add(parcelStateRecord);
        magazine.getParcelStates().add(parcelStateRecord);
        EstimatedDeliveryTimeModel estimatedDeliveryTimeModel = new EstimatedDeliveryTimeModel();
        estimatedDeliveryTimeModel.setTimesAtMagazineToReturn(3);
        when(estimatedDeliveryTimeRepository.getById(anyLong())).thenReturn(estimatedDeliveryTimeModel);
        when(magazineRepository.getById(anyLong())).thenReturn(Optional.of(magazine));
        //when & then
        Assertions.assertThat(sut.getParcelsToReturn(1L).size()).isOne();
    }

}