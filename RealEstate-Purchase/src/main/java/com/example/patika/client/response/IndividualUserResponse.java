package com.example.patika.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualUserResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private String phoneNumber;
    private int advertBalance;
    private LocalDate endDateOfPackage;

    public IndividualUserResponse(int id, String email, String password, String photo, String phoneNumber, String firstName, String lastName,LocalDate endDateOfPackage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.phoneNumber = phoneNumber;
        this.endDateOfPackage = endDateOfPackage;
    }
}