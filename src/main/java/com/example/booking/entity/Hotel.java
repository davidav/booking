package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Long fromCentre;

    @Builder.Default
    private Double rating = 0d;

    @Builder.Default
    private Integer numberOfRatings = 0;

}
