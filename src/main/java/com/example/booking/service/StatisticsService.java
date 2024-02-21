package com.example.booking.service;

import com.example.booking.entity.BookingStatistic;
import com.example.booking.entity.UserStatistic;
import com.example.booking.repo.KafkaBookingRepository;
import com.example.booking.repo.KafkaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService{

    private final KafkaUserRepository kafkaUserRepository;
    private final KafkaBookingRepository kafkaBookingRepository;


    public void addUser(UserStatistic userStatistic) {
        kafkaUserRepository.save(userStatistic);
    }


    public void addBooking(BookingStatistic bookingStatistic) {
        kafkaBookingRepository.save(bookingStatistic);
    }


    public List<UserStatistic> getUserStat() {
        return kafkaUserRepository.findAll();

    }

    public List<BookingStatistic> getBookingStat() {
        return kafkaBookingRepository.findAll();
    }

}
