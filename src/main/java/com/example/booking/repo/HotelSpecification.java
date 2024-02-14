package com.example.booking.repo;

import com.example.booking.dto.hotel.HotelFilter;
import com.example.booking.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter filter){
        return Specification.where(byName(filter.getName()))
                .and(byId(filter.getId()))
                .and(byTitle(filter.getTitle()))
                .and(byCity(filter.getCity()))
                .and(byAddress(filter.getAddress()))
                .and(byFromCentre(filter.getFromCentre()))
                .and(byRating(filter.getRating()))
                .and(byNumberOfRatings(filter.getNumberOfRatings()));
    }

    static Specification<Hotel> byNumberOfRatings(Integer numberOfRatings) {
        return (root, query, criteriaBuilder) -> {
            if (numberOfRatings == null){
                return null;
            }
            return criteriaBuilder.equal(root.get("numberOfRatings"), numberOfRatings);
        };
    }

    static Specification<Hotel> byRating(Double rating) {
        return (root, query, criteriaBuilder) -> {
            if (rating == null){
                return null;
            }
            return criteriaBuilder.equal(root.get("rating"), rating);
        };
    }

    static Specification<Hotel> byFromCentre(Long fromCentre) {
        return (root, query, criteriaBuilder) -> {
            if (fromCentre == null){
                return null;
            }
            return criteriaBuilder.equal(root.get("fromCentre"), fromCentre);
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, cb) -> {
            if (address == null){
                return null;
            }
            return cb.equal(root.get("address"), address);
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, cb) -> {
            if (city == null){
                return null;
            }
            return cb.equal(root.get("city"), city);
        };
    }

    static Specification<Hotel> byName(String name) {
        return (root, query, cb) -> {
            if (name == null){
                return null;
            }
            return cb.equal(root.get("name"), name);
        };
    }

    static Specification<Hotel> byId(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null){
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    static Specification<Hotel> byTitle(String title) {
        return (root, query, cb) -> {
            if (title == null){
                return null;
            }
            return cb.equal(root.get("title"), title);
        };
    }
}
