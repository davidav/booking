package com.example.booking.dto.mapper;

import com.example.booking.dto.room.RoomListRs;
import com.example.booking.dto.room.RoomRq;
import com.example.booking.dto.room.RoomRs;
import com.example.booking.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    default RoomListRs roomListToRoomListResponse(List<Room> rooms){
        RoomListRs response = new RoomListRs();
        response.setRooms(rooms.stream().map(this::roomToResponse).collect(Collectors.toList()));
        return response;
    }

//    @Mapping(target = "hotelId", expression = "java(new com.example.booking.entity.Room.getHotel().getId())")
    RoomRs roomToResponse(Room room);

//    @Mapping(target = "hotel", expression = "java( new com.example.booking.service.HotelService.getById(request.getHotelId))")
    Room requestToRoom(RoomRq request);

}
