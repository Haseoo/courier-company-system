package com.github.haseoo.courier.commandsdata.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class LoginCommandData {
    private String userName;
    private char[] password;

    @JsonCreator
    public LoginCommandData(@JsonProperty(value = "userName", required = true) String userName,
                            @JsonProperty(value = "password", required = true) char[] password) {
        this.userName = userName;
        this.password = password;
    }
}
