package com.github.haseoo.courier.commandsdata.users.employees;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.enums.EmployeeType;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@EqualsAndHashCode(callSuper = true)
@Value
@Validated
public class EmployeeEditCommandData extends UserEditCommandData {
    private EmployeeType employeeType;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;

    @JsonCreator
    public EmployeeEditCommandData(@JsonProperty(value = "employeeType", required = true) EmployeeType employeeType,
                                   @JsonProperty(value = "password") char[] password,
                                   @JsonProperty(value = "name") String name,
                                   @JsonProperty(value = "surname") String surname,
                                   @JsonProperty(value = "phoneNumber") String phoneNumber,
                                   @JsonProperty(value = "pesel") String pesel) {
        super(password);
        this.employeeType = employeeType;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }
}
