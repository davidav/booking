package com.example.booking.repo;

import com.example.booking.entity.Reserve;
import com.example.booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    List<Reserve> findAllByRoom(Room room);

}
