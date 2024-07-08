package com.example.patika.dto.response;

import com.example.patika.model.enums.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {

    private int id;
    private int userId;
    private int packageId;
    private LocalDateTime purchaseDate;
    private PurchaseStatus purchaseStatus;

    public PurchaseResponse(int id, int userId, int packageId, PurchaseStatus purchaseStatus) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.purchaseStatus = purchaseStatus;
    }
}
