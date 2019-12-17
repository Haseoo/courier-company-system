package com.github.haseoo.courier.views.users;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.servicedata.users.UserData;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class UserLoginView {
    private Long id;
    private UserType userType;

    public static UserLoginView of(UserData userData) {
        return UserLoginView
                .builder()
                    .id(userData.getId())
                    .userType(userData.getUserType())
                .build();
    }
}
