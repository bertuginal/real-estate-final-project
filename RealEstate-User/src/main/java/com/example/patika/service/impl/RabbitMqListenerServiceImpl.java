package com.example.patika.service.impl;


import com.example.patika.dto.request.UserBalanceIdentificationRequest;
import com.example.patika.service.IndividiualUserService;
import com.example.patika.service.RabbitMqListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqListenerServiceImpl implements RabbitMqListenerService {


    private IndividiualUserService individiualUserService;

    @Autowired
    public RabbitMqListenerServiceImpl(IndividiualUserService individiualUserService) {
        this.individiualUserService = individiualUserService;
    }


    @RabbitListener(queues = "${realestate.rabbitmq.queue}")
    public void receiveMessage(UserBalanceIdentificationRequest userBalanceIdentificationRequest) {
        log.info(userBalanceIdentificationRequest.toString());
        individiualUserService.updateBalanceByRabbitMq(userBalanceIdentificationRequest.getUserId(), userBalanceIdentificationRequest);
    }

}
