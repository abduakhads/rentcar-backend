package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    private String brand;
    private String model;
    private Double mileage;

    @Column(name = "daily_price")
    private Double dailyPrice;

    private String category;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type")
    private TransmissionType transmissionType;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}