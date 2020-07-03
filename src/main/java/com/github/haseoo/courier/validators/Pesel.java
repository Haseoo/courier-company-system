package com.github.haseoo.courier.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeselValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Pesel {
    String message() default "PESEL '${validatedValue}' is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}