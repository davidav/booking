package com.example.booking.dto.mapper;

import com.example.booking.dto.hotel.HotelListResponse;
import com.example.booking.dto.hotel.HotelResponse;
import com.example.booking.dto.hotel.HotelRequest;
import com.example.booking.dto.hotel.RatingChangeHotelRequest;
import com.example.booking.entity.Hotel;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(HotelMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    default HotelListResponse hotelListToHotelListResponse(List<Hotel> hotels){
        HotelListResponse response = new HotelListResponse();
        response.setHotels(hotels.stream().map(this::hotelToResponse).collect(Collectors.toList()));
        return response;
    }

    HotelResponse hotelToResponse(Hotel hotel);

    Hotel requestToHotel(HotelRequest request);

    Hotel requestToHotel(Long id, HotelRequest request);

    Hotel requestChangeRatingToHotel(RatingChangeHotelRequest request);
}
