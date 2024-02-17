package com.example.booking.dto.mapper;

import com.example.booking.dto.room.RoomListRs;
import com.example.booking.dto.room.RoomRq;
import com.example.booking.dto.room.RoomRs;
import com.example.booking.entity.Room;
import com.example.booking.service.HotelService;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RoomMapper {

    @Autowired
    protected HotelService hotelService;

    public RoomListRs roomListToRoomListResponse(List<Room> rooms) {
        RoomListRs response = new RoomListRs();
        response.setRooms(rooms.stream().map(this::roomToResponse).toList());
        return response;
    }

    @Mapping(target = "hotelId", expression = "java(room.getHotelId())")
    public abstract RoomRs roomToResponse(Room room);

    @Mapping(target = "hotel", expression = "java(hotelService.findById(request.getHotelId()))")
    public abstract Room requestToRoom(RoomRq request);

}
