package com.example.booking.statistics.service;

import com.example.booking.statistics.model.AllStatistics;
import com.example.booking.statistics.model.BookingStatistic;
import com.example.booking.statistics.model.UserStatistic;

import java.util.List;

public interface StatisticsService {

    void addUser(UserStatistic userStatistic);

    void addBooking(BookingStatistic bookingStatistic);

    List<UserStatistic> getUserStat();

    List<BookingStatistic> getBookingStat();

    AllStatistics getAllStat();

}
