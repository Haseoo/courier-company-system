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
public class EmployeeAddCommandData extends UserAddCommandData {
    EmployeeType employeeType;
    private String name;
    private String surname;
    private String phoneNumber;
    private String pesel;

    @JsonCreator
    public EmployeeAddCommandData(@JsonProperty(value = "employeeType", required = true) EmployeeType employeeType,
                                  @JsonProperty(value = "userName", required = true) String userName,
                                  @JsonProperty(value = "password", required = true) char[] password,
                                  @JsonProperty(value = "name", required = true) String name,
                                  @JsonProperty(value = "surname", required = true) String surname,
                                  @JsonProperty(value = "phoneNumber", required = true) String phoneNumber,
                                  @JsonProperty(value = "pesel", required = true) String pesel) {
        super(userName, password);
        this.employeeType = employeeType;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }
}
