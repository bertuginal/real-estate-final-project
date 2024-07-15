package com.example.patika.model;

import com.example.patika.model.enums.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "package_id")
    private int packageId;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_status")
    private PurchaseStatus purchaseStatus;


    public Purchase(int id, int userId, int packageId, PurchaseStatus purchaseStatus) {
        this.id = id;
        this.userId = userId;
        this.packageId = packageId;
        this.purchaseStatus = purchaseStatus;
    }
}
