package com.example.booking.controller;

import com.example.booking.dto.PagesRequest;
import com.example.booking.dto.hotel.*;
import com.example.booking.dto.mapper.HotelMapper;
import com.example.booking.entity.Hotel;
import com.example.booking.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotel")
@Slf4j
public class HotelController {

    private final HotelMapper hotelMapper;
    private final HotelService hotelService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<HotelListResponse> findAll(@Valid PagesRequest request) {
        log.info("HotelController -> findAll");

        return ResponseEntity.ok(
                hotelMapper.hotelListToHotelListResponse(hotelService.findAll(request)));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<HotelResponse> findById(@PathVariable Long id) {
        log.info("HotelController -> findById {}", id);
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(hotelService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid HotelRequest request) {
        log.info("HotelController -> create {}", request);
        Hotel hotel = hotelService.save(hotelMapper.requestToHotel(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelMapper.hotelToResponse(hotel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @RequestBody @Valid HotelRequest request) {
        log.info("HotelController -> update {}", id);
        Hotel hotel = hotelService.update(hotelMapper.requestToHotel(id, request));

        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("HotelController -> deleteById {}", id);
        hotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/rating")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<HotelResponse> changeRating(@RequestBody @Valid RatingChangeHotelRequest request){
        log.info("HotelController -> changeRating");
        Hotel hotel1 = hotelMapper.requestChangeRatingToHotel(request);
        Hotel hotel = hotelService.changeRating(hotel1);

        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotel));
    }

    @GetMapping("/filter")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<HotelListResponse> findAllByFilter(@Valid HotelFilter filter) {
        return ResponseEntity.ok(
                hotelMapper.hotelListToHotelListResponse(hotelService.filterBy(filter)));
    }

}
