package com.example.booking.service;


import com.example.booking.dto.room.RoomFilter;
import com.example.booking.entity.Room;
import com.example.booking.repo.RoomRepository;
import com.example.booking.repo.RoomSpecification;
import com.example.booking.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Room with id {} not found", id).getMessage()));
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room update(Room room) {
        Room existedRoom = findById(room.getId());
        AppHelperUtils.copyNonNullProperties(room, existedRoom);
        return roomRepository.save(existedRoom);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> filterBy(RoomFilter filter) {
//        List<Room> roomsFiltered = roomRepository.findAll(
//                RoomSpecification.withFilter(filter),
//                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
//
//        if (filter.getArrival() != null) {
//            List<Room> rooms = roomRepository.findAll();
//            rooms.removeAll(roomsFiltered);
//            return rooms;
//        }
//
//        return roomsFiltered;
        return roomRepository.findAll(
                RoomSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

}
