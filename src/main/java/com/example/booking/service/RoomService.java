package com.example.booking.service;

import com.example.booking.dto.room.RoomFilter;
import com.example.booking.entity.Room;

import java.util.List;


public interface RoomService {

    Room findById(Long id);

    Room save(Room room);

    Room update(Room room);

    void deleteById(Long id);

    List<Room> filterBy(RoomFilter filter);
}
