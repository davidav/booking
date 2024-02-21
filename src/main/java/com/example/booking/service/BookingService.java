package com.example.booking.service;


import com.example.booking.dto.booking.BookingListRs;
import com.example.booking.dto.booking.BookingRq;
import com.example.booking.dto.booking.BookingRs;
import com.example.booking.dto.mapper.BookingMapper;
import com.example.booking.entity.Booking;
import com.example.booking.entity.Reserve;
import com.example.booking.entity.Room;
import com.example.booking.exception.BookingException;
import com.example.booking.repo.BookingRepository;
import com.example.booking.repo.ReserveRepository;
import com.example.booking.entity.BookingStatistic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final String BOOKED = "Room is booked for these dates";
    private final BookingRepository bookingRepository;
    private final ReserveRepository reserveRepository;
    private final UserService userService;
    private final KafkaTemplate<String, BookingStatistic> bookingKafkaTemplate;
    private final BookingMapper bookingMapper;

    @Value("${app.kafka.kafkaBookingTopic}")
    private String topicBookingName;


    public BookingListRs getAll() {
        return bookingMapper.bookingListToBookingListResponse(bookingRepository.findAll());
    }


    public BookingRs save(BookingRq request, UserDetails userDetails) {

        Booking booking = bookingMapper.requestToBooking(request);
        booking.setUser(userService.findByUsername(userDetails.getUsername()));
        checkBooked(booking);
        Room room = booking.getRoom();
        Reserve savedReserved = reserveRepository.save(bookingMapper.bookingToReserve(booking));
        room.addReserve(savedReserved);
        booking = bookingRepository.save(booking);
        log.info("BookingService -> save. Created booking with id={}", booking.getId());

        bookingKafkaTemplate.send(topicBookingName, bookingMapper.bookingToBookingStatistics(booking));
        log.info("BookingService -> save. Booking send to kafka");

        return bookingMapper.bookingToResponse(booking);
    }


    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    private void checkBooked(Booking booking) {
        log.info("BookingService -> checkBooked ");
        List<Reserve> existReserves = reserveRepository.findAllByRoom(booking.getRoom());
        existReserves.forEach(reserve -> {
            if (reserve.getFromDate().equals(booking.getArrival()) ||
                    reserve.getFromDate().equals(booking.getDeparture()) ||
                    reserve.getToDate().equals(booking.getArrival()) ||
                    reserve.getToDate().equals(booking.getDeparture())) {
                throw new BookingException(BOOKED);
            }
            if (booking.getArrival().isAfter(reserve.getFromDate())) {
                if (booking.getArrival().isBefore(reserve.getToDate())) {
                    throw new BookingException(BOOKED);
                }
            } else {
                if (booking.getDeparture().isAfter(reserve.getFromDate())) {
                    throw new BookingException(BOOKED);
                }
            }
        });
    }
}














