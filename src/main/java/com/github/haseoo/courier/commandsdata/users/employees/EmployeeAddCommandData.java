package com.github.haseoo.courier.commandsdata.users.employees;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.users.UserAddCommandData;
import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.validators.Pesel;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
@Validated
public class EmployeeAddCommandData extends UserAddCommandData {
    @NotNull(message = "Employee must have type")
    EmployeeType employeeType;
    @NotBlank(message = "Employee name cannot be empty")
    private String name;
    @NotBlank(message = "Employee surname cannot be empty")
    private String surname;
    @NotBlank(message = "Employee phone number cannot be empty")
    private String phoneNumber;
    @Pesel
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
