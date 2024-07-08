package com.example.patika.dto.request;


import com.example.patika.model.enums.PurchaseStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {

    @JsonIgnore
    private int id;

    @JsonIgnore
    private int userId;

    private int packageId;

    @JsonIgnore
    private PurchaseStatus purchaseStatus;



}
