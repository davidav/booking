package com.example.booking.statistics.listener;

import com.example.booking.statistics.model.BookingStatistic;
import com.example.booking.statistics.service.StatisticsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaBookingListener {

    private final StatisticsServiceImpl statisticsService;

    @KafkaListener(topics = "${app.kafka.kafkaBookingTopic}",
            groupId = "${app.kafka.kafkaBookingGroupId}",
            containerFactory = "kafkaBookingConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload BookingStatistic bookingStatistic) {
        log.info("Received booking: {}", bookingStatistic);

        statisticsService.addBooking(bookingStatistic);
    }

}
