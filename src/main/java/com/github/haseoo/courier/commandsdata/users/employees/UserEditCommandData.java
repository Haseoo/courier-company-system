package com.github.haseoo.courier.commandsdata.users.employees;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
class UserEditCommandData {
    private char[] password;

    @JsonCreator
    public UserEditCommandData(@JsonProperty(value = "password") char[] password) {
        this.password = password;
    }
}
