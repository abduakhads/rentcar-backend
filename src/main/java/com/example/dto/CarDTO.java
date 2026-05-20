package com.example.dto;

import com.example.models.CarStatus;
import com.example.models.TransmissionType;
import lombok.Data;

@Data
public class CarDTO {
    private String barcode;
    private String brand;
    private String model;
    private Integer numberOfSeats;
    private Double dailyPrice;
    private String category;
    private TransmissionType transmissionType;
    private CarStatus status;
    private String locationCode;
    private String imageUrl;
}