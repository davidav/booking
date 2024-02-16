package com.example.booking.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRs {

    private Long id;
    private String name;
    private String description;
    private Integer number;
    private BigDecimal cost;
    private Integer capacity;
    private Long hotelId;

}
