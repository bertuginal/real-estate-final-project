package com.example.patika.service;

import com.example.patika.dto.request.UserBalanceIdentificationRequest;

public interface RabbitMqListenerService {

    void receiveMessage(UserBalanceIdentificationRequest UserBalanceIdentificationRequest);
}
