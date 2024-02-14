package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer number;
    private BigDecimal cost;
    private Integer capacity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    @ToString.Exclude
    private Hotel hotel;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Reserve> reserves = new ArrayList<>();

    public void addReserve(Reserve reserve){
        if (reserves == null){
            reserves = new ArrayList<>();
        }
        reserves.add(reserve);
    }

}
