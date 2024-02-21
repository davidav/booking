package com.example.booking.dto.mapper;

import com.example.booking.dto.booking.BookingListRs;
import com.example.booking.dto.booking.BookingRq;
import com.example.booking.dto.booking.BookingRs;
import com.example.booking.entity.Booking;
import com.example.booking.entity.Reserve;
import com.example.booking.service.RoomService;
import com.example.booking.entity.BookingStatistic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BookingMapper {

    @Autowired
    protected RoomService roomService;
    public BookingListRs bookingListToBookingListResponse(List<Booking> bookings){
        BookingListRs response = new BookingListRs();
        response.setBookings(
                bookings.stream()
                        .map(this::bookingToResponse)
                        .collect(Collectors.toList()));
        return response;
    }

    @Mapping(target = "roomId", expression = "java(booking.getRoomId())")
    @Mapping(target = "userId", expression = "java(booking.getUserId())")
    public abstract BookingRs bookingToResponse(Booking booking);

    @Mapping(target = "room", expression = "java(roomService.findById(request.getRoomId()))")
    public abstract Booking requestToBooking(BookingRq request);

    @Mapping(target = "fromDate", source = "arrival")
    @Mapping(target = "toDate", source = "departure")
    public abstract Reserve bookingToReserve(Booking booking);

    @Mapping(target = "roomId", expression = "java(booking.getRoomId())")
    @Mapping(target = "userId", expression = "java(booking.getUserId())")
    public abstract BookingStatistic bookingToBookingStatistics(Booking booking);
}
