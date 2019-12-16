package com.github.haseoo.courier.views.users;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.servicedata.users.UserData;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(access = PRIVATE)
public class UserView {
    private Long id;
    private String username;
    private UserType userType;
    private Boolean active;

    public static UserView of(UserData userData) {
        return UserView
                .builder()
                .id(userData.getId())
                .active(userData.getActive())
                .username(userData.getUserName())
                .userType(userData.getUserType())
                .build();
    }
}
