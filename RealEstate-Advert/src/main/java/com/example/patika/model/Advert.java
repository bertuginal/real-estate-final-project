package com.example.patika.model;

import com.example.patika.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "adverts")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "city")
    private City city;

    @Column(name = "district")
    private District district;

    @Enumerated(EnumType.STRING)
    @Column(name = "home_type")
    private HomeType homeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "advert_type")
    private AdvertType advertType;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_rooms")
    private String numberOfRooms;

    @Column(name = "number_of_halls")
    private String numberOfHalls;

    @Column(name = "number_of_bath")
    private String numberOfBath;

    @Column(name = "gross_square_meters")
    private String grossSquareMeters;

    @Column(name = "net_square_meters")
    private String netSquareMeters;

    @Column(name = "floor_number")
    private String floorNumber;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "advert_status")
    private AdvertStatus advertStatus;

    public Advert(int id, int userId, String title, String description, Double price, AdvertStatus advertStatus) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.advertStatus = advertStatus;
    }
}
