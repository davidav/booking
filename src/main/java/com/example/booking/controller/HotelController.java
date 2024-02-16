package com.example.booking.controller;

import com.example.booking.dto.PagesRq;
import com.example.booking.dto.hotel.*;
import com.example.booking.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotel")
@Slf4j
public class HotelController {


    private final HotelService hotelService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public HotelListRs findAll(@Valid PagesRq request) {
        log.info("HotelController -> findAll");
        return hotelService.findAll(request);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public HotelRs findById(@PathVariable Long id) {
        log.info("HotelController -> findById {}", id);
        return hotelService.findByIdRs(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HotelRs create(@RequestBody @Valid HotelRq request) {
        log.info("HotelController -> create {}", request);
        return hotelService.save(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HotelRs update(@PathVariable Long id, @RequestBody @Valid HotelRq request) {
        log.info("HotelController -> update hotel with id={}", id);
        return hotelService.update( id, request);
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
    public HotelRs changeRating(@RequestBody @Valid RatingChangeHotelRq request){
        log.info("HotelController -> changeRating");
        return hotelService.changeRating(request);
    }

    @GetMapping("/filter")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public HotelListRs findAllByFilter(@Valid HotelFilter filter) {
        return hotelService.filterBy(filter);
    }

}
