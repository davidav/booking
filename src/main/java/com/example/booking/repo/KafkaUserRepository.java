package com.example.booking.repo;

import com.example.booking.entity.UserStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface KafkaUserRepository extends MongoRepository<UserStatistic, UUID> {

}
