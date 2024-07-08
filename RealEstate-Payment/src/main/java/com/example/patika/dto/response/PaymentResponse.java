package com.example.patika.dto.response;

import com.example.patika.model.enums.CardType;
import com.example.patika.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private int id;
    private CardType cardType;
    private String cardNumber;
    private String cardHolderName;
    private String cardExpirationDate;
    private String cardCvv;
    private Double amount;
    private CurrencyType currencyType;

}
