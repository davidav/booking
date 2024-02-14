package com.example.booking.validation;

import com.example.booking.dto.PagesRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PagesFilterValidValidator implements ConstraintValidator<PagesFilterValid, PagesRequest> {

    @Override
    public boolean isValid(PagesRequest value, ConstraintValidatorContext context) {
        return !(value.getPageNumber() == null || value.getPageSize() == null);

    }
}
