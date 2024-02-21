package com.example.booking.service;


import com.example.booking.dto.mapper.RoomMapper;
import com.example.booking.dto.room.RoomFilter;
import com.example.booking.dto.room.RoomListRs;
import com.example.booking.dto.room.RoomRq;
import com.example.booking.dto.room.RoomRs;
import com.example.booking.entity.Room;
import com.example.booking.repo.RoomRepository;
import com.example.booking.repo.RoomSpecification;
import com.example.booking.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;

    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Room with id {} not found", id).getMessage()));
    }

    public RoomRs findByIdRs(Long id) {
        return roomMapper.roomToResponse(findById(id));
    }

    public RoomRs save(RoomRq request) {
        return roomMapper.roomToResponse(roomRepository.save(roomMapper.requestToRoom(request)));
    }

    public RoomRs update(Long id, RoomRq request) {
        Room existedRoom = findById(id);
        AppHelperUtils.copyNonNullProperties(roomMapper.requestToRoom(request), existedRoom);

        return roomMapper.roomToResponse(roomRepository.save(existedRoom));
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public RoomListRs filterBy(RoomFilter filter) {
        Specification<Room> spec = RoomSpecification.withFilter(filter);
        PageRequest pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
        List<Room> content = roomRepository.findAll( spec, pageable).getContent();
        List<Room> rooms = content.stream().distinct().toList();
        return roomMapper.roomListToRoomListResponse(rooms);
    }

}
