package com.example.booking.dto.hotel;

import com.example.booking.validation.HotelFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@HotelFilterValid
public class HotelFilter {

    private Integer pageSize;
    private Integer pageNumber;
    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Long fromCentre;
    private Double rating;
    private Integer numberOfRatings;

}
