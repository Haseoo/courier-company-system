package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.IllegalMoveDate;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.IllegalParcelState;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.IncorrectParcelException;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelNotFound;
import com.github.haseoo.courier.models.*;
import com.github.haseoo.courier.repositories.ports.*;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.parcels.ParcelAddData;
import com.github.haseoo.courier.servicedata.parcels.ParcelEditData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;
import com.github.haseoo.courier.servicedata.places.AddressOperationData;
import com.github.haseoo.courier.services.ports.*;
import com.github.haseoo.courier.utilities.PinGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelAtSender;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelInMagazine;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getActiveParcelTypeModel;
import static com.github.haseoo.courier.testutlis.generators.ReceiverInfoDataGenerator.getReceiverInfoModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getIndividualClientModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class ParcelServiceImplTest {
    @Mock
    private ParcelRepository parcelRepository;
    @Mock
    private AddressService addressService;
    @Mock
    private ReceiverInfoService receiverInfoService;
    @Mock
    private ParcelTypeRepository parcelTypeRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientCompanyRepository clientCompanyRepository;
    @Mock
    private ClientIndividualRepository clientIndividualRepository;
    @Mock
    private PinGenerator pinGenerator;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private EmailService emailService;
    @Mock
    private MagazineService magazineService;
    @Mock
    private PostalCodeHelper postalCodeHelper;
    @Mock
    private EstimatedDeliveryTimeRepository estimatedDeliveryTimeRepository;

    @InjectMocks
    private ParcelServiceImpl sut;

    @Test
    void should_add_parcel() {
        //given
        ReceiverInfoModel receiverInfoModel = getReceiverInfoModel();
        ParcelAddData in = ParcelAddData.builder()
                .parcelTypeId(1L)
                .deliveryAddress(AddressOperationData.of(getAddressModel()))
                .senderAddress(AddressOperationData.of(getAddressModel()))
                .parcelFee(BigDecimal.TEN)
                .senderId(1L)
                .receiverContactData(ReceiverInfoOperationData.of(receiverInfoModel))
                .priority(true)
                .build();
        EstimatedDeliveryTimeModel estimatedDeliveryTimeModel = new EstimatedDeliveryTimeModel();
        estimatedDeliveryTimeModel.setExpectedCourierArrivalAfterAddToMagazine(1);
        when(parcelTypeRepository.getById(anyLong())).thenReturn(java.util.Optional.of(getActiveParcelTypeModel()));
        when(clientRepository.getById(anyLong())).thenReturn(java.util.Optional.of(getCompanyClientModel()));
        when(parcelRepository.saveAndFlush(any())).thenReturn(getParcelAtSender());
        when(estimatedDeliveryTimeRepository.getById(anyLong())).thenReturn(estimatedDeliveryTimeModel);
        //when
        sut.add(in);
        //then
        verify(parcelTypeRepository).getById(in.getParcelTypeId());
        verify(clientRepository).getById(in.getSenderId());
    }

    @Test
    void should_edit_parcel() {
        //given
        final long id = 1L;
        ParcelEditData in = ParcelEditData
                .builder()
                .deliveryAddress(AddressOperationData.of(getAddressModel()))
                .senderAddress(AddressOperationData.of(getAddressModel()))
                .parcelFee(BigDecimal.TEN)
                .receiverContactData(ReceiverInfoOperationData.of(getReceiverInfoModel()))
                .priority(false)
                .build();
        when(parcelRepository.getById(anyLong())).thenReturn(java.util.Optional.of(getParcelAtSender()));
        when(parcelRepository.saveAndFlush(any())).thenReturn(getParcelAtSender());
        ArgumentCaptor<ParcelModel> argument = ArgumentCaptor.forClass(ParcelModel.class);
        //when
        sut.edit(1L, in);
        //then
        verify(parcelRepository).getById(id);
        verify(parcelRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getPriority()).isFalse();
        Assertions.assertThat(argument.getValue().getParcelFee()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void should_move_date() {
        //given
        final long id = 1L;
        ParcelModel parcelModel = getParcelAtSender();
        parcelModel.setExpectedCourierArrivalDate(LocalDate.now());
        LocalDate newDate;
        if (LocalDate.now().plusDays(1).getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                LocalDate.now().plusDays(1).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            newDate = LocalDate.now().plusDays(3);
        } else {
            newDate = LocalDate.now().plusDays(1);
        }
        char[] pin = {1, 2, 3, 4};
        parcelModel.setPin(pin);
        when(parcelRepository.getById(anyLong())).thenReturn(java.util.Optional.of(parcelModel));
        when(parcelRepository.saveAndFlush(any())).thenReturn(parcelModel);
        ArgumentCaptor<ParcelModel> argument = ArgumentCaptor.forClass(ParcelModel.class);
        EstimatedDeliveryTimeModel estimatedDeliveryTimeModel = new EstimatedDeliveryTimeModel();
        estimatedDeliveryTimeModel.setMaxMoveDayAfter(4);
        when(parcelRepository.saveAndFlush(any())).thenReturn(getParcelAtSender());
        when(estimatedDeliveryTimeRepository.getById(anyLong())).thenReturn(estimatedDeliveryTimeModel);
        //when
        sut.moveDate(id, pin, newDate);
        verify(parcelRepository).saveAndFlush(argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getExpectedCourierArrivalDate()).isEqualTo(newDate);
        Assertions.assertThat(argument.getValue().getDateMoved()).isTrue();
    }

    @Test
    void should_not_move_date_too_late() {
        //given
        final long id = 1L;
        ParcelModel parcelModel = getParcelAtSender();
        parcelModel.setExpectedCourierArrivalDate(LocalDate.of(2020, 6, 18));
        LocalDate newDate = LocalDate.of(2020, 7, 30);
        char[] pin = {1, 2, 3, 4};
        parcelModel.setPin(pin);
        when(parcelRepository.getById(anyLong())).thenReturn(java.util.Optional.of(parcelModel));
        ArgumentCaptor<ParcelModel> argument = ArgumentCaptor.forClass(ParcelModel.class);
        EstimatedDeliveryTimeModel estimatedDeliveryTimeModel = new EstimatedDeliveryTimeModel();
        estimatedDeliveryTimeModel.setMaxMoveDayAfter(4);
        when(estimatedDeliveryTimeRepository.getById(anyLong())).thenReturn(estimatedDeliveryTimeModel);
        //when & then
        Assertions.assertThatThrownBy(() -> sut.moveDate(id, pin, newDate)).isExactlyInstanceOf(IllegalMoveDate.class);
    }

    @Test
    void should_not_move_date_is_weekend() {
        //given
        final long id = 1L;
        ParcelModel parcelModel = getParcelAtSender();
        parcelModel.setExpectedCourierArrivalDate(LocalDate.of(2020, 6, 18));
        LocalDate newDate = LocalDate.of(2020, 6, 20);
        char[] pin = {1, 2, 3, 4};
        parcelModel.setPin(pin);
        when(parcelRepository.getById(anyLong())).thenReturn(java.util.Optional.of(parcelModel));
        ArgumentCaptor<ParcelModel> argument = ArgumentCaptor.forClass(ParcelModel.class);
        EstimatedDeliveryTimeModel estimatedDeliveryTimeModel = new EstimatedDeliveryTimeModel();
        estimatedDeliveryTimeModel.setMaxMoveDayAfter(4);
        //when & then
        Assertions.assertThatThrownBy(() -> sut.moveDate(id, pin, newDate)).isExactlyInstanceOf(IllegalMoveDate.class);
    }

    @Test
    void should_not_move_date_too_is_before() {
        //given
        final long id = 1L;
        ParcelModel parcelModel = getParcelAtSender();
        parcelModel.setExpectedCourierArrivalDate(LocalDate.of(2020, 6, 18));
        LocalDate newDate = LocalDate.of(2020, 5, 30);
        char[] pin = {1, 2, 3, 4};
        parcelModel.setPin(pin);
        when(parcelRepository.getById(anyLong())).thenReturn(java.util.Optional.of(parcelModel));
        ArgumentCaptor<ParcelModel> argument = ArgumentCaptor.forClass(ParcelModel.class);
        EstimatedDeliveryTimeModel estimatedDeliveryTimeModel = new EstimatedDeliveryTimeModel();
        estimatedDeliveryTimeModel.setMaxMoveDayAfter(4);
        //when & then
        Assertions.assertThatThrownBy(() -> sut.moveDate(id, pin, newDate)).isExactlyInstanceOf(IllegalMoveDate.class);
    }

    @Test
    void should_not_move_bad_pin() {
        //given
        final long id = 1L;
        ParcelModel parcelModel = getParcelAtSender();
        parcelModel.setExpectedCourierArrivalDate(LocalDate.of(2020, 6, 18));
        LocalDate newDate = LocalDate.of(2020, 5, 30);
        char[] pin = {1, 2, 3, 4};
        parcelModel.setPin(pin);
        when(parcelRepository.getById(anyLong())).thenReturn(java.util.Optional.of(parcelModel));
        ArgumentCaptor<ParcelModel> argument = ArgumentCaptor.forClass(ParcelModel.class);
        //when & then
        Assertions.assertThatThrownBy(() -> sut.moveDate(id, new char[]{0, 0, 0, 0}, newDate)).isExactlyInstanceOf(IncorrectParcelException.class);
    }

    @Test
    void should_get_by_id() {
        //given
        final long id = 1L;
        ParcelModel parcelModel = getParcelAtSender();
        when(parcelRepository.getById(any())).thenReturn(java.util.Optional.of(parcelModel));
        //when
        sut.getById(id);
        //then
        verify(parcelRepository).getById(id);
    }

    @Test
    void should_return_list() {
        //given
        when(parcelRepository.getList()).thenReturn(Arrays.asList(getParcelAtSender()));
        //when
        sut.getList();
        //then
        verify(parcelRepository).getList();
    }

    @Test
    void should_delete_parcel_at_sender() {
        //given
        final long id = 1L;
        ParcelModel parcelModel = getParcelAtSender();
        when(parcelRepository.getById(anyLong())).thenReturn(java.util.Optional.of(parcelModel));
        //when
        sut.delete(id);
        //then
        verify(parcelRepository).delete(id);
    }

    @Test
    void should_throw_exception_when_deleting_non_existent_parcel() {
        //given
        when(parcelRepository.getById(anyLong())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.delete(1L)).isExactlyInstanceOf(ParcelNotFound.class);
    }

    @Test
    void should_not_mark_parcel_at_sender_to_return() {
        //given
        final long id = 1L;
        ParcelModel parcelModel = getParcelAtSender();
        parcelModel.setId(id);
        when(parcelRepository.getById(anyLong())).thenReturn(java.util.Optional.of(parcelModel));
        //when & then
        Assertions.assertThatThrownBy(() -> sut.setParcelToReturn(id)).isExactlyInstanceOf(IllegalParcelState.class);
        verify(parcelRepository).getById(id);
    }

    @Test
    void should_mark_parcel_in_magazine_to_return_client_company() {
        //given
        final long id = 1L;
        final char[] newPin = new char[]{1, 2, 3, 4};
        ParcelModel parcelModel = getParcelInMagazine();
        parcelModel.setId(id);
        ClientCompanyModel clientCompanyModel = (ClientCompanyModel) parcelModel.getSender();
        when(clientRepository.getById(any())).thenReturn(Optional.of(parcelModel.getSender()));
        when(clientCompanyRepository.getById(any())).thenReturn(Optional.of(clientCompanyModel));
        when(pinGenerator.getParcelPin()).thenReturn(newPin);
        when(parcelRepository.saveAndFlush(any())).thenReturn(parcelModel);
        when(parcelRepository.getById(anyLong())).thenReturn(Optional.of(parcelModel));
        ArgumentCaptor<ReceiverInfoOperationData> newReceiver = ArgumentCaptor.forClass(ReceiverInfoOperationData.class);
        ArgumentCaptor<AddressOperationData> newAddress = ArgumentCaptor.forClass(AddressOperationData.class);
        ArgumentCaptor<ParcelModel> updatedParcel = ArgumentCaptor.forClass(ParcelModel.class);
        //when
        sut.setParcelToReturn(id);
        verify(receiverInfoService).consume(newReceiver.capture(), any());
        verify(addressService).consume(newAddress.capture(), any());
        verify(parcelRepository).saveAndFlush(updatedParcel.capture());
        //then
        verify(parcelRepository).getById(id);
        verify(emailService).sentReturnNotification(any());
        Assertions.assertThat(newAddress.getValue().getCity())
                .isEqualTo(parcelModel.getSenderAddress().getCity());
        Assertions.assertThat(newAddress.getValue().getPostalCode())
                .isEqualTo(parcelModel.getSenderAddress().getPostalCode());
        Assertions.assertThat(newAddress.getValue().getStreet())
                .isEqualTo(parcelModel.getSenderAddress().getStreet());
        Assertions.assertThat(newAddress.getValue().getBuildingNumber())
                .isEqualTo(parcelModel.getSenderAddress().getBuildingNumber());
        Assertions.assertThat(newAddress.getValue().getFlatNumber())
                .isEqualTo(parcelModel.getSenderAddress().getFlatNumber());

        Assertions.assertThat(newReceiver.getValue().getEmailAddress()).
                isEqualTo(clientCompanyModel.getEmailAddress());
        Assertions.assertThat(newReceiver.getValue().getName()).
                isEqualTo(clientCompanyModel.getCompanyName());
        Assertions.assertThat(newReceiver.getValue().getPhoneNumber()).
                isEqualTo(clientCompanyModel.getPhoneNumber());
        Assertions.assertThat(newReceiver.getValue().getSurname()).
                isEqualTo("-");

        Assertions.assertThat(updatedParcel.getValue().getToReturn()).isTrue();
        Assertions.assertThat(updatedParcel.getValue().getPin()).isEqualTo(newPin);
    }

    @Test
    void should_mark_parcel_in_magazine_to_return_client_individual() {
        //given
        final long id = 1L;
        final char[] newPin = new char[]{1, 2, 3, 4};
        ParcelModel parcelModel = getParcelInMagazine();
        parcelModel.setId(id);
        ClientIndividualModel clientIndividualModel = getIndividualClientModel();
        clientIndividualModel.setId(1L);
        parcelModel.setSender(clientIndividualModel);
        when(clientRepository.getById(any())).thenReturn(Optional.of(parcelModel.getSender()));
        when(clientIndividualRepository.getById(any())).thenReturn(Optional.of(clientIndividualModel));
        when(pinGenerator.getParcelPin()).thenReturn(newPin);
        when(parcelRepository.saveAndFlush(any())).thenReturn(parcelModel);
        when(parcelRepository.getById(anyLong())).thenReturn(Optional.of(parcelModel));
        ArgumentCaptor<ReceiverInfoOperationData> newReceiver = ArgumentCaptor.forClass(ReceiverInfoOperationData.class);
        ArgumentCaptor<AddressOperationData> newAddress = ArgumentCaptor.forClass(AddressOperationData.class);
        ArgumentCaptor<ParcelModel> updatedParcel = ArgumentCaptor.forClass(ParcelModel.class);
        //when
        sut.setParcelToReturn(id);
        verify(receiverInfoService).consume(newReceiver.capture(), any());
        verify(addressService).consume(newAddress.capture(), any());
        verify(parcelRepository).saveAndFlush(updatedParcel.capture());
        //then
        verify(parcelRepository).getById(id);
        verify(emailService).sentReturnNotification(any());
        Assertions.assertThat(newAddress.getValue().getCity())
                .isEqualTo(parcelModel.getSenderAddress().getCity());
        Assertions.assertThat(newAddress.getValue().getPostalCode())
                .isEqualTo(parcelModel.getSenderAddress().getPostalCode());
        Assertions.assertThat(newAddress.getValue().getStreet())
                .isEqualTo(parcelModel.getSenderAddress().getStreet());
        Assertions.assertThat(newAddress.getValue().getBuildingNumber())
                .isEqualTo(parcelModel.getSenderAddress().getBuildingNumber());
        Assertions.assertThat(newAddress.getValue().getFlatNumber())
                .isEqualTo(parcelModel.getSenderAddress().getFlatNumber());

        Assertions.assertThat(newReceiver.getValue().getEmailAddress()).
                isEqualTo(clientIndividualModel.getEmailAddress());
        Assertions.assertThat(newReceiver.getValue().getName()).
                isEqualTo(clientIndividualModel.getName());
        Assertions.assertThat(newReceiver.getValue().getPhoneNumber()).
                isEqualTo(clientIndividualModel.getPhoneNumber());
        Assertions.assertThat(newReceiver.getValue().getSurname()).
                isEqualTo(clientIndividualModel.getSurname());

        Assertions.assertThat(updatedParcel.getValue().getToReturn()).isTrue();
        Assertions.assertThat(updatedParcel.getValue().getPin()).isEqualTo(newPin);
    }

}