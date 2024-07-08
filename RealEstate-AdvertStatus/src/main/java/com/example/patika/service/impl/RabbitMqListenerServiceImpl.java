package com.example.patika.service.impl;

import com.example.patika.dto.request.AdvertStatusRequest;
import com.example.patika.service.AdvertStatusService;
import com.example.patika.service.RabbitMqListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqListenerServiceImpl implements RabbitMqListenerService {

    private AdvertStatusService advertStatusService;

    @Autowired
    public RabbitMqListenerServiceImpl(AdvertStatusService advertStatusService) {
        this.advertStatusService = advertStatusService;
    }

    @RabbitListener(queues = "${realestate.rabbitmq.queue}")
    public void receiveMessage(AdvertStatusRequest advertStatusRequest) {
        log.info(advertStatusRequest.toString());
        advertStatusService.updateStatus(advertStatusRequest);
    }

}
