package com.example.patika.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceResponse {
    private int id;
    private int advertBalance;
    private LocalDate endDateOfPackage;
}
