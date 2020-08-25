package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.exceptions.ResourceException;
import com.github.haseoo.courier.models.ClientCompanyModel;
import com.github.haseoo.courier.repositories.ports.ClientRepository;
import com.github.haseoo.courier.security.UserDetailsImpl;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.security.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private ClientServiceImpl sut;

    @Test
    void should_return_list_with_one_element() {
        //given
        ClientCompanyModel clientCompanyModel = getCompanyClientModel();
        clientCompanyModel.setSentParcels(new ArrayList<>());
        clientCompanyModel.setObservedParcels(new ArrayList<>());
        when(clientRepository.getList()).thenReturn(Arrays.asList(clientCompanyModel));
        //when & then
        Assertions.assertThat(sut.getList().size()).isOne();
    }

    @Test
    void should_query_id() {
        //given
        final long id = 1L;
        ClientCompanyModel clientCompanyModel = getCompanyClientModel();
        clientCompanyModel.setSentParcels(new ArrayList<>());
        clientCompanyModel.setObservedParcels(new ArrayList<>());
        when(clientRepository.getById(anyLong())).thenReturn(Optional.of(clientCompanyModel));
        UserDetailsImpl userDetails = new UserDetailsImpl(id,
                "",
                null,
                UserRole.CLIENT,
                UserType.COMPANY_CLIENT,
                true);
        when(userDetailsService.currentUser()).thenReturn(userDetails);
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.getById(id);
        verify(clientRepository).getById(argument.capture());
        //then
        Assertions.assertThat(argument.getValue()).isEqualTo(id);
    }

    @Test
    void should_throw_exception_when_checking_other_user() {
        //given
        final long id = 1L;
        UserDetailsImpl userDetails = new UserDetailsImpl(id + 1,
                "",
                null,
                UserRole.CLIENT,
                UserType.COMPANY_CLIENT,
                true);
        when(userDetailsService.currentUser()).thenReturn(userDetails);
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(id)).isExactlyInstanceOf(ResourceException.class);
    }
}