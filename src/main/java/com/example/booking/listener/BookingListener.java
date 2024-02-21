package com.example.booking.listener;

import com.example.booking.entity.BookingStatistic;
import com.example.booking.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookingListener {

    private final StatisticsService statisticsService;

    @KafkaListener(topics = "${app.kafka.kafkaBookingTopic}",
            groupId = "${app.kafka.kafkaBookingGroupId}",
            containerFactory = "kafkaBookingConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload BookingStatistic bookingStatistic) {
        log.info("Received booking: {}", bookingStatistic);

        statisticsService.addBooking(bookingStatistic);
    }

}
