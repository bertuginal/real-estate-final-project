package com.example.patika.queue;

import com.example.patika.dto.request.AdvertStatusRequest;

public interface QueueService {
	
	void sendMessage(AdvertStatusRequest advertStatusRequest);

}
