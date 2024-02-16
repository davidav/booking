package com.example.booking.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelRs {

    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Long fromCentre;
    private Double rating;
    private Integer numberOfRatings;

}
