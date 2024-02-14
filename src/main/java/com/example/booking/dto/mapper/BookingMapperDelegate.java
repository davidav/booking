package com.example.booking.dto.mapper;

import com.example.booking.dto.booking.BookingRequest;
import com.example.booking.dto.booking.BookingResponse;
import com.example.booking.entity.Booking;
import com.example.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BookingMapperDelegate implements BookingMapper{

    @Autowired
    private RoomService roomService;
    @Override
    public BookingResponse bookingToResponse(Booking booking) {
        return BookingResponse.builder()
                .arrival(booking.getArrival())
                .departure(booking.getDeparture())
                .roomId(booking.getRoom().getId())
                .userId(booking.getUser().getId())
                .build();
    }

    @Override
    public Booking requestToBooking(BookingRequest request) {
        return Booking.builder()
                .arrival(request.getArrival())
                .departure(request.getDeparture())
                .room(roomService.findById(request.getRoomId()))
                .build();
    }

}
