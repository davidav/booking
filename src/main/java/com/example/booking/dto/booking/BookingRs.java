package com.example.booking.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRs {

    private Instant arrival;
    private Instant departure;
    private Long roomId;;
    private Long userId;

}
