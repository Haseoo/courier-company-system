package com.github.haseoo.courier.commandsdata.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Getter
public class LoginCommandData {
    @NotBlank(message = "Login cannot be empty")
    private String userName;
    private char[] password;

    @JsonCreator
    public LoginCommandData(@JsonProperty(value = "username", required = true) String userName,
                            @JsonProperty(value = "password", required = true) char[] password) {
        this.userName = userName;
        this.password = password;
    }
}
