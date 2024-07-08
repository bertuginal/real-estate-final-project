package com.example.patika.dto.request;

import com.example.patika.model.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertUpdateRequest {

    @JsonIgnore
    private int id;

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

    public AdvertUpdateRequest(int id,  String title, String description, Double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }
}
