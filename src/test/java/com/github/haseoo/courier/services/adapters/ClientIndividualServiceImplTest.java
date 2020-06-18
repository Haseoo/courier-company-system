package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientWithPeselExists;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.repositories.ports.ClientIndividualRepository;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualEditData;
import com.github.haseoo.courier.services.ports.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getIndividualClientModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class ClientIndividualServiceImplTest {
    @Mock
    private ClientIndividualRepository clientIndividualRepository;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private ClientIndividualServiceImpl sut;

    @Test
    void should_query_by_id() {
        //given
        final long id = 1L;
        when(clientIndividualRepository.getById(any())).thenReturn(Optional.of(getIndividualClientModel()));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.getById(id);
        verify(clientIndividualRepository).getById(argument.capture());
        //then
        Assertions.assertThat(argument.getValue()).isEqualTo(id);
    }

    @Test
    void should_throw_exception_when_client_not_exists() {
        //given
        when(clientIndividualRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(1L)).isExactlyInstanceOf(ClientNotFound.class);
    }

    @Test
    void should_add_client() {
        //given
        ClientIndividualAddData in = ClientIndividualAddData.builder()
                .userName("test")
                .password(new char[0])
                .active(true)
                .emailAddress("test@test.ts")
                .phoneNumber("123456")
                .name("testName")
                .surname("testSurname")
                .pesel("77111295117")
                .clientType(ClientType.INDIVIDUAL)
                .build();
        when(clientIndividualRepository.saveAndFlush(any())).thenReturn(getIndividualClientModel());
        when(passwordEncoder.encode(any())).thenReturn("");
        ArgumentCaptor<ClientIndividualModel> argument = ArgumentCaptor.forClass(ClientIndividualModel.class);
        //when
        sut.add(in);
        verify(clientIndividualRepository).saveAndFlush(argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getUserName()).isEqualTo(in.getUserName());
        Assertions.assertThat(argument.getValue().getActive()).isEqualTo(in.getActive());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getPesel()).isEqualTo(in.getPesel());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(argument.getValue().getClientType()).isEqualTo(in.getClientType());
    }

    @Test
    void should_throw_exception_when_invalid_pesel_exists() {
        //given
        ClientIndividualAddData in = ClientIndividualAddData.builder()
                .userName("test")
                .password(new char[0])
                .active(true)
                .emailAddress("test@test.ts")
                .phoneNumber("123456")
                .name("testName")
                .surname("testSurname")
                .pesel("77111295317")
                .clientType(ClientType.INDIVIDUAL)
                .build();
        when(clientIndividualRepository.getByPesel(any())).thenReturn(Optional.of(getIndividualClientModel()));
        //when & then
        Assertions.assertThatThrownBy(() -> sut.add(in)).isExactlyInstanceOf(ClientWithPeselExists.class);
    }

    @Test
    void should_edit_client() {
        final long id = 1L;
        ClientIndividualEditData in = ClientIndividualEditData.builder()
                .password(new char[0])
                .emailAddress("test@test.ts")
                .phoneNumber("123456")
                .name("test")
                .surname("test")
                .build();
        when(clientIndividualRepository.saveAndFlush(any())).thenReturn(getIndividualClientModel());
        when(clientIndividualRepository.getById(any())).thenReturn(Optional.of(getIndividualClientModel()));
        when(passwordEncoder.encode(any())).thenReturn("");
        ArgumentCaptor<ClientIndividualModel> argument = ArgumentCaptor.forClass(ClientIndividualModel.class);
        ArgumentCaptor<Long> idArgument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.edit(id, in);
        verify(clientIndividualRepository).saveAndFlush(argument.capture());
        verify(clientIndividualRepository).getById(idArgument.capture());

        //then
        Assertions.assertThat(idArgument.getValue()).isEqualTo(id);
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
    }

}