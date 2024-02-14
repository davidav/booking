package com.example.booking.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllStatistics {

    private List<UserStatistic> users;
    private List<BookingStatistic> bookings;

}
