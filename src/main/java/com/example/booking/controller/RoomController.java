package com.example.booking.controller;

import com.example.booking.dto.room.RoomFilter;
import com.example.booking.dto.room.RoomListRs;
import com.example.booking.dto.room.RoomRq;
import com.example.booking.dto.room.RoomRs;
import com.example.booking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public RoomRs findById(@PathVariable Long id) {
        log.info("RoomController -> findById {}", id);
        return roomService.findByIdRs(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RoomRs create(@RequestBody @Valid RoomRq rq) {
        log.info("RoomController -> create {}", rq.getName());
        return roomService.save(rq);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RoomRs update(@PathVariable Long id, @RequestBody RoomRq rq) {
        log.info("RoomController -> update id={} request={}", id, rq);
        return roomService.update(id, rq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(@PathVariable Long id) {
        log.info("RoomController -> deleteById {}", id);
        roomService.deleteById(id);
    }

    @GetMapping("/filter")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public RoomListRs findAllByFilter(@Valid RoomFilter filter) {
        log.info("RoomController -> findAllByFilter {}", filter);
        return roomService.filterBy(filter);
    }

}
