package com.example.patika.dto.response;

import com.example.patika.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertResponse {

    private int id;
    private int userId;
    private City city;
    private District district;
    private HomeType homeType;
    private AdvertType advertType;
    private String title;
    private String description;
    private String numberOfRooms;
    private String numberOfHalls;
    private String numberOfBath;
    private String grossSquareMeters;
    private String netSquareMeters;
    private String floorNumber;
    private Double price;
    private LocalDate createdDate;
    private LocalDate endDate;
    private AdvertStatus advertStatus;

    public AdvertResponse(int id, int userId, String title, String description, Double price, AdvertStatus advertStatus) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.advertStatus = advertStatus;
    }
}
