package com.example.booking.validation;

import com.example.booking.dto.room.RoomFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoomFilterValidValidator implements ConstraintValidator<RoomFilterValid, RoomFilter> {

    @Override
    public boolean isValid(RoomFilter value, ConstraintValidatorContext context) {

        if(value.getPageNumber() == null || value.getPageSize() == null){
            return false;
        }

        return (value.getArrival() == null || value.getDeparture() != null) &&
                (value.getArrival() != null || value.getDeparture() == null);
    }
}
