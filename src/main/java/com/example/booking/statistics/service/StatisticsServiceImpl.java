package com.example.booking.statistics.service;

import com.example.booking.statistics.model.AllStatistics;
import com.example.booking.statistics.model.BookingStatistic;
import com.example.booking.statistics.model.UserStatistic;
import com.example.booking.statistics.repo.KafkaBookingRepository;
import com.example.booking.statistics.repo.KafkaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{

    private final KafkaUserRepository kafkaUserRepository;
    private final KafkaBookingRepository kafkaBookingRepository;

    @Override
    public void addUser(UserStatistic userStatistic) {
        kafkaUserRepository.save(userStatistic);
    }

    @Override
    public void addBooking(BookingStatistic bookingStatistic) {
        kafkaBookingRepository.save(bookingStatistic);
    }

    @Override
    public List<UserStatistic> getUserStat() {
        return kafkaUserRepository.findAll();

    }
    @Override
    public List<BookingStatistic> getBookingStat() {
        return kafkaBookingRepository.findAll();
    }

    @Override
    public AllStatistics getAllStat() {
        return AllStatistics.builder()
                .users(getUserStat())
                .bookings(getBookingStat())
                .build();
    }
}
