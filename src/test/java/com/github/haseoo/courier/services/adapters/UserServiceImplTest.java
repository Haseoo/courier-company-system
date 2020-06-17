package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.InvalidLoginOrPassword;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.UserNotFoundException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.UsernameIsTaken;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.models.UserModel;
import com.github.haseoo.courier.repositories.ports.UserRepository;
import com.github.haseoo.courier.servicedata.users.UserData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
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

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getUserModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private UserServiceImpl sut;


    @Test
    void should_return_list_with_users() {
        //given
        List<UserModel> userModelList = Arrays.asList(getUserModel());
        when(userRepository.getList()).thenReturn(userModelList);
        //then
        List<UserData> out = sut.getList();
        //them
        Assertions.assertThat(out).hasSameSizeAs(userModelList);
    }

    @Test
    void should_set_inactive() {
        //given
        final long id = 1L;
        UserModel userModel = getUserModel();
        when(userRepository.getById(id)).thenReturn(Optional.of(userModel));
        when(userRepository.saveAndFlush(any())).thenReturn(userModel);
        ArgumentCaptor<UserModel> argument = ArgumentCaptor.forClass(UserModel.class);
        //when
        UserData out = sut.setAsInactive(userModel.getId());
        verify(userRepository).saveAndFlush(argument.capture());
        //then
        Assertions.assertThat(out.getId()).isEqualTo(id);
        Assertions.assertThat(argument.getValue().getActive()).isFalse();
    }

    @Test
    void should_set_active() {
        //given
        final long id = 1L;
        UserModel userModel = getUserModel();
        when(userRepository.getById(id)).thenReturn(Optional.of(userModel));
        when(userRepository.saveAndFlush(any())).thenReturn(userModel);
        ArgumentCaptor<UserModel> argument = ArgumentCaptor.forClass(UserModel.class);
        //when
        UserData out = sut.setAsActive(userModel.getId());
        verify(userRepository).saveAndFlush(argument.capture());
        //then
        Assertions.assertThat(out.getId()).isEqualTo(id);
        Assertions.assertThat(argument.getValue().getActive()).isTrue();
    }

    @Test
    void should_get_by_username() {
        //given
        final String username = "test";
        UserModel userModel = getUserModel();
        when(userRepository.getByUsername(any())).thenReturn(Optional.of(userModel));
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        //when
        UserData out = sut.getByUsername(username);
        verify(userRepository).getByUsername(argument.capture());
        //then
        Assertions.assertThat(out.getUserName()).isEqualTo(userModel.getUserName());
        Assertions.assertThat(argument.getValue()).isEqualTo(username);
    }

    @Test
    void should_not_throw_exception_when_user_with_username_not_exists() {
        //given
        when(userRepository.getByUsername(any())).thenReturn(Optional.empty());
        //when & then
        sut.checkUsername("");
    }



    @Test
    void should_throw_exception_when_user_not_found_setting_active() {
        //given
        when(userRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.setAsActive(0L)).isExactlyInstanceOf(UserNotFoundException.class);
    }

    @Test
    void should_throw_exception_when_user_not_found_setting_inactive() {
        //given
        when(userRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.setAsInactive(0L)).isExactlyInstanceOf(UserNotFoundException.class);
    }

    @Test
    void should_throw_exception_when_user_not_found_getting_by_name() {
        //given
        when(userRepository.getByUsername(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getByUsername("")).isExactlyInstanceOf(InvalidLoginOrPassword.class);
    }

    @Test
    void should_trow_exception_when_username_is_taken() {
        UserModel userModel = getUserModel();
        when(userRepository.getByUsername(any())).thenReturn(Optional.of(userModel));
        //when & then
        Assertions.assertThatThrownBy(() -> sut.checkUsername("")).isExactlyInstanceOf(UsernameIsTaken.class);
    }
}