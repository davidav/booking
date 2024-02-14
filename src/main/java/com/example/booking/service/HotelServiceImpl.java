package com.example.booking.service;

import com.example.booking.dto.PagesRequest;
import com.example.booking.dto.hotel.HotelFilter;
import com.example.booking.entity.Hotel;
import com.example.booking.repo.HotelRepository;
import com.example.booking.repo.HotelSpecification;
import com.example.booking.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Hotel with id {} not found", id).getMessage()));
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        Hotel existedHotel = findById(hotel.getId());
        AppHelperUtils.copyNonNullProperties(hotel, existedHotel);
        return hotelRepository.save(existedHotel);
    }

    @Override
    public void deleteById(Long id) {
        hotelRepository.deleteById(id);

    }

    @Override
    public List<Hotel> findAll(PagesRequest request) {
        return hotelRepository.findAll(
                PageRequest.of(request.getPageNumber(), request.getPageSize())).getContent();
    }

    @Override
    public Hotel changeRating(Hotel hotel) {
        Hotel existHotel = findById(hotel.getId());
        Double rating = existHotel.getRating();
        Double newMark = hotel.getRating();
        Integer numberOfRating = existHotel.getNumberOfRatings();
        if (numberOfRating == 0) {
            rating = newMark;
        } else {
            double totalRating = rating * numberOfRating;
            totalRating = totalRating - rating + newMark;
            rating = Math.round((totalRating / (numberOfRating)) * 10) / 10.0;

        }

        existHotel.setRating(rating);
        existHotel.setNumberOfRatings(numberOfRating + 1);

        return update(existHotel);
    }

    @Override
    public List<Hotel> filterBy(HotelFilter filter) {
        return hotelRepository.findAll(
                HotelSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }
}
