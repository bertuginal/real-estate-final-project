package com.example.patika.model;

import com.example.patika.model.enums.CardType;
import com.example.patika.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType = CardType.MASTERCARD;

    @Column(name = "card_number")
    private String cardNumber = "2555 5555 5555 5555";

    @Column(name = "card_holder_name")
    private String cardHolderName = "BERTUĞ İNAL";

    @Column(name = "card_expiration_date")
    private LocalDate cardExpirationDate = LocalDate.of(2024,15,07);

    @Column(name = "card_cvv")
    private String cardCvv = "225";

    @Column(name = "amount")
    private Double amount ;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type")
    private CurrencyType currencyType = CurrencyType.TL;


}
