package com.example.booking.dto.hotel;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HotelListResponse {

    private List<HotelResponse> hotels;

}
