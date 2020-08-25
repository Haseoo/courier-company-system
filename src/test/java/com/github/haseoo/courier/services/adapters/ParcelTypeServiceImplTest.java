package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelTypeCannotBeRemoved;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelTypeFeeCannotBeChanged;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelTypeNotFound;
import com.github.haseoo.courier.models.ParcelTypeModel;
import com.github.haseoo.courier.repositories.ports.ParcelTypeRepository;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeAddOperationData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeEditOperationData;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getActiveParcelTypeModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getNotActiveParcelTypeModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class ParcelTypeServiceImplTest {
    @Mock
    private ParcelTypeRepository parcelTypeRepository;

    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private ParcelTypeServiceImpl sut;

    @Test
    void should_add_parcel() {
        //given
        ParcelTypeAddOperationData parcelTypeAddOperationData = ParcelTypeAddOperationData
                .builder()
                .name("test")
                .description("test")
                .active(true)
                .price(BigDecimal.ONE)
                .build();
        when(parcelTypeRepository.saveAndFlush(any())).thenReturn(getActiveParcelTypeModel());
        ArgumentCaptor<ParcelTypeModel> argument = ArgumentCaptor.forClass(ParcelTypeModel.class);
        //when
        sut.add(parcelTypeAddOperationData);
        //then
        verify(parcelTypeRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(parcelTypeAddOperationData.getName());
        Assertions.assertThat(argument.getValue().getDescription()).isEqualTo(parcelTypeAddOperationData.getDescription());
        Assertions.assertThat(argument.getValue().getActive()).isEqualTo(parcelTypeAddOperationData.getActive());
        Assertions.assertThat(argument.getValue().getPrice()).isEqualTo(parcelTypeAddOperationData.getPrice());
    }

    @Test
    void should_edit_parcel_type() {
        //given
        ParcelTypeEditOperationData parcelTypeEditOperationData = ParcelTypeEditOperationData
                .builder()
                .name("test2change")
                .description("test2change")
                .active(true)
                .price(BigDecimal.TEN)
                .build();
        ParcelTypeModel parcelTypeModel = getNotActiveParcelTypeModel();
        parcelTypeModel.setParcels(new ArrayList<>());
        when(parcelTypeRepository.getById(any())).thenReturn(Optional.of(parcelTypeModel));
        when(parcelTypeRepository.saveAndFlush(any())).thenReturn(parcelTypeModel);
        parcelTypeModel.setId(1L);
        ArgumentCaptor<ParcelTypeModel> argument = ArgumentCaptor.forClass(ParcelTypeModel.class);
        //when
        sut.edit(1L, parcelTypeEditOperationData);
        //then
        verify(parcelTypeRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(parcelTypeEditOperationData.getName());
        Assertions.assertThat(argument.getValue().getDescription()).isEqualTo(parcelTypeEditOperationData.getDescription());
        Assertions.assertThat(argument.getValue().getActive()).isEqualTo(parcelTypeEditOperationData.getActive());
        Assertions.assertThat(argument.getValue().getPrice()).isEqualTo(parcelTypeEditOperationData.getPrice());
    }

    @Test
    void should_throw_exception_when_edit_non_existent_parcel() {
        //given
        ParcelTypeEditOperationData parcelTypeEditOperationData = ParcelTypeEditOperationData.builder().build();
        when(parcelTypeRepository.getById(anyLong())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(1L, parcelTypeEditOperationData)).isExactlyInstanceOf(ParcelTypeNotFound.class);
    }

    @Test
    void should_throw_exception_when_unpaid_parcel() {
        //given
        ParcelTypeEditOperationData parcelTypeEditOperationData = ParcelTypeEditOperationData
                .builder()
                .name("test2change")
                .description("test2change")
                .active(true)
                .price(BigDecimal.TEN)
                .build();
        ParcelTypeModel parcelTypeModel = getNotActiveParcelTypeModel();
        parcelTypeModel.setParcels(Arrays.asList(getParcelModel(parcelTypeModel, null, null, null)));
        when(parcelTypeRepository.getById(any())).thenReturn(Optional.of(parcelTypeModel));
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(1L, parcelTypeEditOperationData)).isExactlyInstanceOf(ParcelTypeFeeCannotBeChanged.class);
    }

    @Test
    void should_query_only_active_list() {
        //given && when
        sut.getList(true);
        //then
        verify(parcelTypeRepository).getActiveTypes();
        verify(parcelTypeRepository, never()).getList();
    }

    @Test
    void should_query_entire_list() {
        //given && then
        sut.getList(false);
        verify(parcelTypeRepository).getList();
        verify(parcelTypeRepository, never()).getActiveTypes();
    }

    @Test
    void should_query_by_id() {
        //given
        final long id = 1L;
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        when(parcelTypeRepository.getById(any())).thenReturn(Optional.of(getActiveParcelTypeModel()));
        //when
        sut.getById(id);
        //then
        verify(parcelTypeRepository).getById(argument.capture());
        Assertions.assertThat(argument.getValue()).isEqualTo(id);
    }

    @Test
    void should_throw_exception_when_parcel_type_with_id_not_found() {
        //given
        when(parcelTypeRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(1L)).isExactlyInstanceOf(ParcelTypeNotFound.class);
    }

    @Test
    void should_delete_parcel_with_id() {
        //given
        final long id = 1L;
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        ParcelTypeModel parcelTypeModel = getNotActiveParcelTypeModel();
        parcelTypeModel.setParcels(new ArrayList<>());
        when(parcelTypeRepository.getById(any())).thenReturn(Optional.of(parcelTypeModel));
        //when
        sut.delete(id);
        //then
        verify(parcelTypeRepository).getById(id);
    }

    @Test
    void should_throw_exception_when_delete_non_existent_type() {
        //given
        when(parcelTypeRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.delete(1L)).isExactlyInstanceOf(ParcelTypeNotFound.class);
    }

    @Test
    void should_throw_exception_when_delete_tye_with_parcels() {
        //given
        ParcelTypeModel parcelTypeModel = getNotActiveParcelTypeModel();
        parcelTypeModel.setParcels(Arrays.asList(getParcelModel(parcelTypeModel, null, null, null)));
        when(parcelTypeRepository.getById(any())).thenReturn(Optional.of(parcelTypeModel));
        //when & then
        Assertions.assertThatThrownBy(() -> sut.delete(1L)).isExactlyInstanceOf(ParcelTypeCannotBeRemoved.class);
    }

}