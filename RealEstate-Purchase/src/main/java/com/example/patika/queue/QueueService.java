package com.example.patika.queue;

import com.example.patika.dto.request.UserBalanceIdentificationRequest;

public interface QueueService {

    void sendMessage(UserBalanceIdentificationRequest userBalanceIdentificationRequest);

}
