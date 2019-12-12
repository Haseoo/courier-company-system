package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.UserNotFoundException;
import com.github.haseoo.courier.models.UserModel;
import com.github.haseoo.courier.repositories.ports.UserRepository;
import com.github.haseoo.courier.servicedata.users.UserData;
import com.github.haseoo.courier.services.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserData> getList() {
        return userRepository
                .getList()
                .stream()
                .map(UserData::of)
                .collect(Collectors.toList());
    }

    @Override
    public UserData setAsInactive(Long id) {
        UserModel userModel = userRepository.getById(id).orElseThrow(() -> new UserNotFoundException(id));
        userModel.setActive(false);
        return UserData.of(userRepository.saveAndFlush(userModel));
    }
}
