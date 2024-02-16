package com.example.booking.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelRq {

    private String name;

    private String title;

    private String city;

    private String address;

    private Long fromCentre;

}
