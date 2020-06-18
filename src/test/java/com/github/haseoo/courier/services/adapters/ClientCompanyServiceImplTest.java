package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.exceptions.serviceexceptions.InvalidEmailAddress;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientWithNipExists;
import com.github.haseoo.courier.models.ClientCompanyModel;
import com.github.haseoo.courier.repositories.ports.ClientCompanyRepository;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyEditData;
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
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class ClientCompanyServiceImplTest {
    @Mock
    private ClientCompanyRepository clientCompanyRepository;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private ClientCompanyServiceImpl sut;

    @Test
    void should_query_by_id() {
        //given
        final long id = 1L;
        when(clientCompanyRepository.getById(any())).thenReturn(Optional.of(getCompanyClientModel()));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.getById(id);
        verify(clientCompanyRepository).getById(argument.capture());
        //then
        Assertions.assertThat(argument.getValue()).isEqualTo(id);
    }

    @Test
    void should_throw_exception_when_client_not_exists() {
        //given
        when(clientCompanyRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(1L)).isExactlyInstanceOf(ClientNotFound.class);
    }

    @Test
    void should_add_client() {
        ClientCompanyAddData in = ClientCompanyAddData.builder()
                .userName("test")
                .password(new char[0])
                .active(true)
                .emailAddress("test@test.ts")
                .phoneNumber("123456")
                .companyName("test")
                .nip("123")
                .representativeName("test")
                .representativeSurname("test")
                .representativeEmailAddress("tst@tets.ts")
                .representativePhoneNumber("1234")
                .clientType(ClientType.COMPANY)
                .build();
        when(clientCompanyRepository.saveAndFlush(any())).thenReturn(getCompanyClientModel());
        when(passwordEncoder.encode(any())).thenReturn("");
        ArgumentCaptor<ClientCompanyModel> argument = ArgumentCaptor.forClass(ClientCompanyModel.class);
        //when
        sut.add(in);
        verify(clientCompanyRepository).saveAndFlush(argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getUserName()).isEqualTo(in.getUserName());
        Assertions.assertThat(argument.getValue().getActive()).isEqualTo(in.getActive());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getCompanyName()).isEqualTo(in.getCompanyName());
        Assertions.assertThat(argument.getValue().getNip()).isEqualTo(in.getNip());
        Assertions.assertThat(argument.getValue().getRepresentativeName()).isEqualTo(in.getRepresentativeName());
        Assertions.assertThat(argument.getValue().getRepresentativePhoneNumber()).isEqualTo(in.getRepresentativePhoneNumber());
        Assertions.assertThat(argument.getValue().getRepresentativeEmailAddress()).isEqualTo(in.getRepresentativeEmailAddress());
        Assertions.assertThat(argument.getValue().getClientType()).isEqualTo(in.getClientType());
    }

    @Test
    void should_throw_exception_when_nip_exists() {
        //given
        ClientCompanyAddData in = ClientCompanyAddData.builder()
                .userName("test")
                .password(new char[0])
                .active(true)
                .emailAddress("test@test.ts")
                .phoneNumber("123456")
                .companyName("test")
                .nip("123")
                .representativeName("test")
                .representativeSurname("test")
                .representativeEmailAddress("tst@tets.ts")
                .representativePhoneNumber("1234")
                .clientType(ClientType.COMPANY)
                .build();
        when(clientCompanyRepository.getByNip(any())).thenReturn(Optional.of(getCompanyClientModel()));
        //when & then
        Assertions.assertThatThrownBy(() -> sut.add(in)).isExactlyInstanceOf(ClientWithNipExists.class);
    }

    @Test
    void should_edit_company_client() {
        final long id = 1L;
        ClientCompanyEditData in = ClientCompanyEditData.builder()
                .password(new char[0])
                .emailAddress("test@test.ts")
                .phoneNumber("123456")
                .companyName("test")
                .representativeName("test")
                .representativeSurname("test")
                .representativeEmailAddress("tst@tets.ts")
                .representativePhoneNumber("1234")
                .build();
        when(clientCompanyRepository.saveAndFlush(any())).thenReturn(getCompanyClientModel());
        when(clientCompanyRepository.getById(any())).thenReturn(Optional.of(getCompanyClientModel()));
        when(passwordEncoder.encode(any())).thenReturn("");
        ArgumentCaptor<ClientCompanyModel> argument = ArgumentCaptor.forClass(ClientCompanyModel.class);
        ArgumentCaptor<Long> idArgument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.edit(id, in);
        verify(clientCompanyRepository).saveAndFlush(argument.capture());
        verify(clientCompanyRepository).getById(idArgument.capture());

        //then
        Assertions.assertThat(idArgument.getValue()).isEqualTo(id);
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getCompanyName()).isEqualTo(in.getCompanyName());
        Assertions.assertThat(argument.getValue().getRepresentativeName()).isEqualTo(in.getRepresentativeName());
        Assertions.assertThat(argument.getValue().getRepresentativePhoneNumber()).isEqualTo(in.getRepresentativePhoneNumber());
        Assertions.assertThat(argument.getValue().getRepresentativeEmailAddress()).isEqualTo(in.getRepresentativeEmailAddress());
    }

    @Test
    void should_throw_exception_when_incorrect_email1() {
        //given
        ClientCompanyEditData in = ClientCompanyEditData.builder().emailAddress("test").build();
        when(clientCompanyRepository.getById(any())).thenReturn(Optional.of(getCompanyClientModel()));
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(1L, in)).isExactlyInstanceOf(InvalidEmailAddress.class);
    }

    @Test
    void should_throw_exception_when_incorrect_email2() {
        //given
        ClientCompanyEditData in = ClientCompanyEditData.builder().emailAddress("test@test.ts").representativeEmailAddress("test").build();
        when(clientCompanyRepository.getById(any())).thenReturn(Optional.of(getCompanyClientModel()));
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(1L, in)).isExactlyInstanceOf(InvalidEmailAddress.class);
    }

    @Test
    void should_throw_exception_when_edit_not_existent_client() {
        //given
        ClientCompanyEditData in = ClientCompanyEditData.builder().build();
        when(clientCompanyRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(1L, in)).isExactlyInstanceOf(ClientNotFound.class);

    }
}