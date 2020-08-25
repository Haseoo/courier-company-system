package com.github.haseoo.courier.commandsdata.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserPatchCommandData {
    boolean active;

    @JsonCreator
    public UserPatchCommandData(@JsonProperty(value = "active", required = true) boolean active) {
        this.active = active;
    }
}
