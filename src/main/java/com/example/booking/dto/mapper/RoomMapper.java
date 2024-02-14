package com.example.booking.dto.mapper;

import com.example.booking.dto.hotel.HotelListResponse;
import com.example.booking.dto.room.RoomListResponse;
import com.example.booking.dto.room.RoomResponse;
import com.example.booking.dto.room.RoomRequest;
import com.example.booking.entity.Hotel;
import com.example.booking.entity.Room;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    RoomResponse roomToResponse(Room room);

    Room requestToRoom(RoomRequest request);

    Room requestToRoom(Long id, RoomRequest request);

    default RoomListResponse roomListToRoomListResponse(List<Room> rooms){
        RoomListResponse response = new RoomListResponse();
        response.setRooms(rooms.stream().map(this::roomToResponse).collect(Collectors.toList()));
        return response;
    }


}
