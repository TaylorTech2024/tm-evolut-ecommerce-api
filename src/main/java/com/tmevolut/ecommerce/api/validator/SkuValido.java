package com.tmevolut.ecommerce.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SkuValidoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkuValido {
    String message() default "SKU deve seguir o padrão TM-0001";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}