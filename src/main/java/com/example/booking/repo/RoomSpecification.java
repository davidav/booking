package com.example.booking.repo;

import com.example.booking.dto.room.RoomFilter;
import com.example.booking.entity.Room;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter filter) {

        return Specification.where(
                        byId(filter.getId()))
                .and(byName(filter.getName()))
                .and(byCost(filter.getMinCost(), filter.getMaxCost()))
                .and(byCapacity(filter.getCapacity()))
                .and(byHotelId(filter.getHotelId()))
                .and(byDates(filter.getArrival(), filter.getDeparture()));

    }

    static Specification<Room> byDates(Instant arrival, Instant departure) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.not(
                        criteriaBuilder.or(
                                criteriaBuilder.or(
                                        criteriaBuilder.between(root.get("reserves").get("fromDate"), arrival, departure),
                                        criteriaBuilder.between(root.get("reserves").get("toDate"), arrival, departure)
                                ),
                                criteriaBuilder.and(
                                        criteriaBuilder.lessThanOrEqualTo(root.get("reserves").get("fromDate"), arrival),
                                        criteriaBuilder.greaterThanOrEqualTo(root.get("reserves").get("toDate"), departure))));
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return (root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("hotel").get("id"), hotelId);
        };
    }

    static Specification<Room> byCapacity(Integer capacity) {
        return (root, query, criteriaBuilder) -> {
            if (capacity == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("capacity"), capacity);
        };
    }

    static Specification<Room> byId(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    static Specification<Room> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    static Specification<Room> byCost(BigDecimal minCost, BigDecimal maxCost) {
        return (root, query, criteriaBuilder) -> {
            if (minCost == null && maxCost == null) {
                return null;
            }
            if (minCost == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxCost);
            }
            if (maxCost == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minCost);
            }
            return criteriaBuilder.between(root.get("cost"), minCost, maxCost);
        };
    }

}
