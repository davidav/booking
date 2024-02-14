package com.example.booking.dto.mapper;

import com.example.booking.dto.booking.BookingListResponse;
import com.example.booking.dto.booking.BookingRequest;
import com.example.booking.dto.booking.BookingResponse;
import com.example.booking.entity.Booking;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(BookingMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {
    default BookingListResponse bookingListToBookingListResponse(List<Booking> bookings){
        BookingListResponse response = new BookingListResponse();
        response.setBookings(
                bookings.stream()
                        .map(this::bookingToResponse)
                        .collect(Collectors.toList()));
        return response;

    }

    BookingResponse bookingToResponse(Booking booking);

    Booking requestToBooking(BookingRequest request);

}
