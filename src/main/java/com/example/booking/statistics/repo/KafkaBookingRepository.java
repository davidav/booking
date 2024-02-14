package com.example.booking.statistics.repo;

import com.example.booking.statistics.model.BookingStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface KafkaBookingRepository extends MongoRepository<BookingStatistic, UUID> {



}
