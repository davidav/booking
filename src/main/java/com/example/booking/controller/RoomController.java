package com.example.booking.controller;

import com.example.booking.dto.mapper.RoomMapper;
import com.example.booking.dto.room.RoomFilter;
import com.example.booking.dto.room.RoomListResponse;
import com.example.booking.dto.room.RoomRequest;
import com.example.booking.dto.room.RoomResponse;
import com.example.booking.entity.Room;
import com.example.booking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
@Slf4j
public class RoomController {
    private final RoomMapper roomMapper;
    private final RoomService roomService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        log.info("RoomController -> findById {}", id);

        return ResponseEntity.ok(
                roomMapper.roomToResponse(roomService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid RoomRequest request) {
        log.info("RoomController -> create {}", request.getName());
        Room room = roomService.save(roomMapper.requestToRoom(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.roomToResponse(roomService.save(room)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @RequestBody RoomRequest request) {
        log.info("RoomController -> update id={} request={}", id, request);
        Room room1 = roomMapper.requestToRoom(id, request);
        Room room = roomService.update(room1);

        return ResponseEntity.ok(roomMapper.roomToResponse(room));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("RoomController -> deleteById {}", id);
        roomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<RoomListResponse> findAllByFilter(@Valid RoomFilter filter) {
        log.info("RoomController -> findAllByFilter {}", filter);
        return ResponseEntity.ok(
                roomMapper.roomListToRoomListResponse(roomService.filterBy(filter)));
    }

}
