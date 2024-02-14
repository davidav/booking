package com.example.booking.dto.mapper;

import com.example.booking.dto.hotel.HotelRequest;
import com.example.booking.dto.hotel.RatingChangeHotelRequest;
import com.example.booking.entity.Hotel;

public abstract class HotelMapperDelegate implements HotelMapper{

    @Override
    public Hotel requestToHotel(Long id, HotelRequest request) {
        Hotel hotel = requestToHotel(request);
        hotel.setId(id);

        return hotel;
    }

    @Override
    public Hotel requestChangeRatingToHotel(RatingChangeHotelRequest request) {
        return Hotel.builder()
                .id(request.getHotelId())
                .rating(request.getNewMark())
                .build();
    }
}
