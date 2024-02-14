package com.example.booking.controller;

import com.example.booking.dto.booking.BookingListResponse;
import com.example.booking.dto.booking.BookingRequest;
import com.example.booking.dto.booking.BookingResponse;
import com.example.booking.dto.mapper.BookingMapper;
import com.example.booking.entity.Booking;
import com.example.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
@Slf4j
public class BookingController {

    private final BookingMapper bookingMapper;
    private final BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingListResponse> getAll(){

        return ResponseEntity.ok()
                .body(bookingMapper.bookingListToBookingListResponse(bookingService.getAll()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails){
        log.info("BookingController -> create {}", request);
        Booking booking = bookingService.save(bookingMapper.requestToBooking(request), userDetails);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingMapper.bookingToResponse(booking));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("BookingController -> deleteById {}", id);
        bookingService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
