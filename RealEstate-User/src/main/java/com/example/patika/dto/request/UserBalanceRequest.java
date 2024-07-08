package com.example.patika.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceRequest {

    @JsonIgnore
    private int id;
    private int advertBalance;
    private LocalDate endDateOfPackage;

}
