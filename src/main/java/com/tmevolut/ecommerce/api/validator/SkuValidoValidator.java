package com.tmevolut.ecommerce.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SkuValidoValidator implements ConstraintValidator<SkuValido, String> {
    @Override public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("TM-[0-9]{4,}");
    }
}
