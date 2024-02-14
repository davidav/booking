package com.example.booking.dto.room;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class RoomListResponse {

    private List<RoomResponse> rooms;

}
