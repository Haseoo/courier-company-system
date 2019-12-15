package com.github.haseoo.courier.commandsdata.users.employees;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.validation.annotation.Validated;

@EqualsAndHashCode(callSuper = true)
@Value
@Validated
public class EmployeeAddCommandData extends UserAddCommandData{
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;

    @JsonCreator
    public EmployeeAddCommandData(@JsonProperty(value = "userName", required = true) String userName,
                                  @JsonProperty(value = "password", required = true) char[] password,
                                  @JsonProperty(value = "name", required = true)String name,
                                  @JsonProperty(value = "password", required = true) String surname,
                                  @JsonProperty(value = "password", required = true) String phoneNumber,
                                  @JsonProperty(value = "password", required = true) String pesel) {
        super(userName, password);
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }
}
