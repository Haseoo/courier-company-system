package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.commandsdata.users.LoginCommandData;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.InvalidLoginOrPassword;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.UserNotFoundException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.UsernameIsTaken;
import com.github.haseoo.courier.models.UserModel;
import com.github.haseoo.courier.repositories.ports.UserRepository;
import com.github.haseoo.courier.security.JwtAuthenticationResponse;
import com.github.haseoo.courier.security.JwtTokenProvider;
import com.github.haseoo.courier.servicedata.users.UserData;
import com.github.haseoo.courier.services.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    @Override
    public List<UserData> getList() {
        return userRepository
                .getList()
                .stream()
                .map(UserData::of)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserData setAsInactive(Long id) {
        UserModel userModel = userRepository.getById(id).orElseThrow(() -> new UserNotFoundException(id));
        userModel.setActive(false);
        return UserData.of(userRepository.saveAndFlush(userModel));
    }

    @Transactional
    @Override
    public UserData setAsActive(Long id) {
        UserModel userModel = userRepository.getById(id).orElseThrow(() -> new UserNotFoundException(id));
        userModel.setActive(true);
        return UserData.of(userRepository.saveAndFlush(userModel));
    }

    @Override
    public UserData getByUsername(String userName) {
        UserModel userModel = userRepository.getByUsername(userName).orElseThrow(InvalidLoginOrPassword::new);
        return UserData.of(userModel);
    }

    @Override
    public JwtAuthenticationResponse getJwt(LoginCommandData loginCommandData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCommandData.getUserName(),
                        String.valueOf(loginCommandData.getPassword())
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public void checkUsername(String username) {
        userRepository.getByUsername(username).ifPresent(userModel -> {
            throw new UsernameIsTaken(userModel.getUserName());
        });
    }
}
