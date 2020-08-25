package com.github.haseoo.courier.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.github.haseoo.courier.utilities.UserUtils.tryPesel;

public class PeselValidator  implements ConstraintValidator<Pesel, String> {
    @Override
    public boolean isValid(String pesel, ConstraintValidatorContext context) {
        return tryPesel(pesel);
    }
}
