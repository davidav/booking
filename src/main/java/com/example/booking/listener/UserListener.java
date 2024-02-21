package com.example.booking.listener;

import com.example.booking.entity.UserStatistic;
import com.example.booking.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserListener {

    private final StatisticsService statisticsService;

    @KafkaListener(topics = "${app.kafka.kafkaUserTopic}",
            groupId = "${app.kafka.kafkaUserGroupId}",
            containerFactory = "kafkaUserConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload UserStatistic userStatistic) {
        log.info("Received user: {}", userStatistic);

        statisticsService.addUser(userStatistic);
    }

}
