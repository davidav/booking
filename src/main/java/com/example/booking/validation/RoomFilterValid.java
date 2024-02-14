package com.example.booking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoomFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomFilterValid {

    String message() default "Pagination fields must be specified." +
            "If you use arrival or departure dates, they must be filled in together";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
