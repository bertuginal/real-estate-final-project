package com.example.patika.service;

import com.example.patika.dto.request.AdvertStatusRequest;

public interface RabbitMqListenerService {

    void receiveMessage(AdvertStatusRequest advertStatusRequest);
}
