package com.example.patika.client.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceRequest {

    private int id;
    private int advertBalance;
    private LocalDate endDateOfPackage;
}
