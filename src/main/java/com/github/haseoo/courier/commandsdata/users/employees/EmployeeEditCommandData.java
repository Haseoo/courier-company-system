package com.github.haseoo.courier.commandsdata.users.employees;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.haseoo.courier.commandsdata.users.UserEditCommandData;
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
public class EmployeeEditCommandData extends UserEditCommandData {
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
