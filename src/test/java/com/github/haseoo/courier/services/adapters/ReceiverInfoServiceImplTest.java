package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ReceiverInfoNotFound;
import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.querydata.ReceiverInfoQueryData;
import com.github.haseoo.courier.repositories.ports.ReceiverInfoRepository;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoData;
import com.github.haseoo.courier.servicedata.parcels.ReceiverInfoOperationData;
import com.github.haseoo.courier.services.ports.ReceiverInfoService;
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
import static com.github.haseoo.courier.testutlis.generators.ReceiverInfoDataGenerator.getReceiverInfoModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class ReceiverInfoServiceImplTest {

    @Mock
    private ReceiverInfoRepository receiverInfoRepository;

    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private ReceiverInfoServiceImpl sut;

    @Test
    void should_consume_existing_record() {
        //given
        Consumer<ReceiverInfoModel> consumer = e -> {};
        ReceiverInfoOperationData receiverInfoOperationData = ReceiverInfoOperationData.of(getReceiverInfoModel());
        when(receiverInfoRepository.receiverInfoExists(ReceiverInfoQueryData.of(receiverInfoOperationData))).thenReturn(Optional.of(getReceiverInfoModel()));
        //when
        sut.consume(receiverInfoOperationData, consumer);
        //then
        verify(receiverInfoRepository, never()).saveAndFlush(any());
    }

    @Test
    void should_consume_new_record() {
        //given
        Consumer<ReceiverInfoModel> consumer = e -> {};
        ReceiverInfoOperationData receiverInfoOperationData = ReceiverInfoOperationData.builder()
                .emailAddress("test@test.ts")
                .name("test")
                .phoneNumber("test")
                .surname("test")
                .build();
        when(receiverInfoRepository.receiverInfoExists(any())).thenReturn(Optional.empty());
        ArgumentCaptor<ReceiverInfoModel> argument =  ArgumentCaptor.forClass(ReceiverInfoModel.class);
        //when
        sut.consume(receiverInfoOperationData, consumer);
        //then
        verify(receiverInfoRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(receiverInfoOperationData.getEmailAddress());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(receiverInfoOperationData.getName());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(receiverInfoOperationData.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(receiverInfoOperationData.getSurname());
    }

    @Test
    void should_search_for_record_with_id() {
        //given
        final long id = 1L;
        ReceiverInfoModel receiverInfoModel = getReceiverInfoModel();
        receiverInfoModel.setId(id);
        when(receiverInfoRepository.getById(any())).thenReturn(Optional.of(receiverInfoModel));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        //when
        ReceiverInfoData out = sut.getById(id);
        //then
        verify(receiverInfoRepository).getById(argument.capture());
        Assertions.assertThat(argument.getValue()).isEqualTo(id);
        Assertions.assertThat(out.getId()).isEqualTo(ReceiverInfoData.of(receiverInfoModel).getId());
    }

    @Test
    void should_throw_exception_when_record_not_exist() {
        //given
        when(receiverInfoRepository.getById(anyLong())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(1L)).isExactlyInstanceOf(ReceiverInfoNotFound.class);
    }

    @Test
    void should_return_list_with_one_element() {
        //given
        List<ReceiverInfoModel> list = Arrays.asList(getReceiverInfoModel());
        when(receiverInfoRepository.getList()).thenReturn(list);
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(1);
    }

}