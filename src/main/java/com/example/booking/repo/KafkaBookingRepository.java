package com.example.booking.repo;

import com.example.booking.entity.BookingStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface KafkaBookingRepository extends MongoRepository<BookingStatistic, UUID> {



}
