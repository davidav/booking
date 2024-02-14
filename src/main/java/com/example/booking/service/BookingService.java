package com.example.booking.service;

import com.example.booking.entity.Booking;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface BookingService {

    List<Booking> getAll();

    Booking save(Booking booking, UserDetails userDetails);

    void deleteById(Long id);
}
