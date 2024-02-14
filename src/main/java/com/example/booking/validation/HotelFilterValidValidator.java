package com.example.booking.validation;

import com.example.booking.dto.hotel.HotelFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class HotelFilterValidValidator implements ConstraintValidator<HotelFilterValid, HotelFilter> {
    @Override
    public boolean isValid(HotelFilter value, ConstraintValidatorContext context) {
        return value.getPageNumber() != null && value.getPageSize() != null;
    }
}
