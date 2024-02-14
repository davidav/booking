package com.example.booking.statistics.listener;

import com.example.booking.statistics.model.UserStatistic;
import com.example.booking.statistics.service.StatisticsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaUserListener {

    private final StatisticsServiceImpl statisticsService;

    @KafkaListener(topics = "${app.kafka.kafkaUserTopic}",
            groupId = "${app.kafka.kafkaUserGroupId}",
            containerFactory = "kafkaUserConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload UserStatistic userStatistic) {
        log.info("Received user: {}", userStatistic);

        statisticsService.addUser(userStatistic);
    }

}
