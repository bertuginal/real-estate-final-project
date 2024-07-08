package com.example.patika.client.response;

import com.example.patika.model.enums.*;

import java.time.LocalDate;

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
}
