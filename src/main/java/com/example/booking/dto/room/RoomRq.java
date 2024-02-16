package com.example.booking.dto.room;

import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRq {

    private String name;
    private String description;
    private Integer number;
    private BigDecimal cost;
    private Integer capacity;
    private Long hotelId;

}
