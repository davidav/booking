package com.example.booking.dto.mapper;

import com.example.booking.dto.hotel.HotelListRs;
import com.example.booking.dto.hotel.HotelRs;
import com.example.booking.dto.hotel.HotelRq;
import com.example.booking.entity.Hotel;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    default HotelListRs hotelListToHotelListResponse(List<Hotel> hotels){
        HotelListRs response = new HotelListRs();
        response.setHotels(hotels.stream().map(this::hotelToResponse).collect(Collectors.toList()));
        return response;
    }

    HotelRs hotelToResponse(Hotel hotel);

    Hotel requestToHotel(HotelRq rq);

}
