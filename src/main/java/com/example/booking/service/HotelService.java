package com.example.booking.service;

import com.example.booking.dto.PagesRq;
import com.example.booking.dto.hotel.*;
import com.example.booking.dto.mapper.HotelMapper;
import com.example.booking.entity.Hotel;
import com.example.booking.repo.HotelRepository;
import com.example.booking.repo.HotelSpecification;
import com.example.booking.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;

    public Hotel findById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Hotel with id {} not found", id).getMessage()));
    }

    public HotelRs findByIdRs(Long id) {
        return hotelMapper.hotelToResponse(findById(id));
    }

    public HotelRs save(HotelRq request) {
        return hotelMapper.hotelToResponse(
                hotelRepository.save(hotelMapper.requestToHotel(request)));
    }

    public HotelRs update(Long id, HotelRq request) {
        Hotel existedHotel = findById(id);
        AppHelperUtils.copyNonNullProperties(hotelMapper.requestToHotel(request), existedHotel);
        return hotelMapper.hotelToResponse(hotelRepository.save(existedHotel));
    }

    public void deleteById(Long id) {
        hotelRepository.deleteById(id);

    }

    public HotelListRs findAll(PagesRq request) {
        return hotelMapper.hotelListToHotelListResponse(hotelRepository.findAll(
                        PageRequest.of(request.getPageNumber(), request.getPageSize()))
                .getContent());
    }

    public HotelRs changeRating(RatingChangeHotelRq request) {
        Hotel hotel = findById(request.getHotelId());
        Double rating = hotel.getRating();
        Double newMark = request.getNewMark();
        Integer numberOfRating = hotel.getNumberOfRatings();
        if (numberOfRating == 0) {
            rating = newMark;
        } else {
            double totalRating = rating * numberOfRating;
            totalRating = totalRating - rating + newMark;
            rating = Math.round((totalRating / (numberOfRating)) * 10) / 10.0;

        }
        hotel.setRating(rating);
        hotel.setNumberOfRatings(numberOfRating + 1);

        return hotelMapper.hotelToResponse(hotelRepository.save(hotel));
    }

    public HotelListRs filterBy(HotelFilter filter) {
        return hotelMapper.hotelListToHotelListResponse(hotelRepository.findAll(
                        HotelSpecification.withFilter(filter),
                        PageRequest.of(filter.getPageNumber(), filter.getPageSize()))
                .getContent());
    }
}
