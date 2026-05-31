package com.tmevolut.ecommerce.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class SkuValidoValidator implements ConstraintValidator<SkuValido, String> {

    private static final Pattern SKU_PATTERN = Pattern.compile("^TM-[0-9]{4}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return SKU_PATTERN.matcher(value).matches();
    }
}