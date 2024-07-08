package com.example.patika.queue;

import com.example.patika.config.RabbitMqConfig;
import com.example.patika.dto.request.UserBalanceIdentificationRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService implements QueueService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private RabbitMqConfig config;

    @Override
    public void sendMessage(UserBalanceIdentificationRequest userBalanceIdentificationRequest) {
        rabbitTemplate.convertAndSend(config.getExchange(), config.getRoutingkey(), userBalanceIdentificationRequest);

    }

}
