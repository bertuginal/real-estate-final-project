package com.example.patika.service;

import com.example.patika.dto.request.AdvertStatusRequest;
import com.example.patika.model.Advert;
import com.example.patika.model.enums.AdvertStatus;
import com.example.patika.service.impl.AdvertStatusServiceImpl;
import com.example.patika.service.impl.RabbitMqListenerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class RabbitMqListenerServiceTest {
    @InjectMocks
    private RabbitMqListenerServiceImpl rabbitMqListenerService;

    @Mock
    private AdvertStatusServiceImpl advertStatusService;






    @Test
    void updateAdvert() {

        AdvertStatusRequest request = prepareAdvertStatusRequest(1);



       rabbitMqListenerService .receiveMessage(request);
       Mockito.verify(advertStatusService).updateStatus(any());


    }


    private Advert prepareAdvert(int id) {
        return new Advert(id, AdvertStatus.IN_REVIEW);

    }

    private AdvertStatusRequest prepareAdvertStatusRequest(int id) {
        return new AdvertStatusRequest(id, AdvertStatus.IN_REVIEW);

    }

}
