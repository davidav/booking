package com.example.booking.controller;

import com.example.booking.dto.booking.BookingListRs;
import com.example.booking.dto.booking.BookingRq;
import com.example.booking.dto.booking.BookingRs;
import com.example.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
@Slf4j
public class BookingController {


    private final BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookingListRs getAll(){
        return bookingService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public BookingRs create(@RequestBody BookingRq request,
                            @AuthenticationPrincipal UserDetails userDetails){
        log.info("BookingController -> create {}", request);
        return bookingService.save(request, userDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(@PathVariable Long id) {
        log.info("BookingController -> deleteById {}", id);
        bookingService.deleteById(id);
    }
}
