package com.example.patika.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualUserRequest {

    @JsonIgnore
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private String phoneNumber;

}
