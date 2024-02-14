package com.example.booking.dto.room;

import com.example.booking.validation.PagesFilterValid;
import com.example.booking.validation.RoomFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@RoomFilterValid
public class RoomFilter {

    private Integer pageSize;
    private Integer pageNumber;
    private Long id;
    private String name;
    private BigDecimal minCost;
    private BigDecimal maxCost;
    private Integer capacity;
    private Instant arrival;
    private Instant departure;
    private Long hotelId;

}
