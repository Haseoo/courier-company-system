package com.github.haseoo.courier.commandsdata.users.employees;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@EqualsAndHashCode(callSuper = true)
@Value
@Validated
public class EmployeeEditCommandData extends UserEditCommandData{
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;

    @JsonCreator
    public EmployeeEditCommandData(@JsonProperty(value = "password") char[] password,
                                   @JsonProperty(value = "name")String name,
                                   @JsonProperty(value = "password") String surname,
                                   @JsonProperty(value = "password") String phoneNumber,
                                   @JsonProperty(value = "password") String pesel) {
        super(password);
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }
}
