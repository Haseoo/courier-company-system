package com.github.haseoo.courier.servicedata.users;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.models.UserModel;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
public class UserData {
    private Long id;
    private String userName;
    private char[] password;
    private Boolean active;
    private UserType userType;

    public static UserData of(UserModel userModel) {
        return UserData
                .builder()
                .id(userModel.getId())
                .active(userModel.getActive())
                .password(userModel.getPassword())
                .userType(UserUtils.getUserType(userModel))
                .build();
    }
}
