package com.example.booking.validation;

import com.example.booking.dto.PagesRq;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PagesFilterValidValidator implements ConstraintValidator<PagesFilterValid, PagesRq> {

    @Override
    public boolean isValid(PagesRq value, ConstraintValidatorContext context) {
        return !(value.getPageNumber() == null || value.getPageSize() == null);

    }
}
