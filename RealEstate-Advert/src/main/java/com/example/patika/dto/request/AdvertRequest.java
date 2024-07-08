package com.example.patika.dto.request;

import com.example.patika.model.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertRequest {

    @JsonIgnore
    private int id;

    @JsonIgnore
    private int userId;

    private City city;
    private District district;
    private AdvertType advertType;
    private HomeType homeType;
    private String title;
    private String description;
    private String numberOfRooms;
    private String numberOfHalls;
    private String numberOfBath;
    private String grossSquareMeters;
    private String netSquareMeters;
    private String floorNumber;
    private Double price;

    @JsonIgnore
    private LocalDate createdDate;

    @JsonIgnore
    private LocalDate endDate;

    @JsonIgnore
    private AdvertStatus advertStatus;

    public AdvertRequest(int id, int userId, String title, String description, Double price, AdvertStatus advertStatus) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.advertStatus = advertStatus;
    }
}
