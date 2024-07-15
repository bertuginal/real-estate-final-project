package com.example.patika.service.impl;

import com.example.patika.client.AdvertClient;
import com.example.patika.dto.request.AdvertStatusRequest;
import com.example.patika.model.Advert;
import com.example.patika.repository.AdvertDao;
import com.example.patika.service.AdvertStatusService;
import com.example.patika.utils.dtoConverter.DtoConverterService;
import com.example.patika.utils.results.Result;
import com.example.patika.utils.results.SuccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertStatusServiceImpl implements AdvertStatusService {

    private AdvertDao advertDao;
    private DtoConverterService dtoConverterService;
    private AdvertClient advertClient;

    @Autowired
    public AdvertStatusServiceImpl(AdvertDao advertDao, DtoConverterService dtoConverterService, AdvertClient advertClient) {
        this.advertDao = advertDao;
        this.dtoConverterService = dtoConverterService;
        this.advertClient = advertClient;
    }

    // Queue'dan gelen ilan id ve status bilgisine göre ilan statüsü güncelleme işlemi
    @Override
    public Result updateStatus(AdvertStatusRequest advertRequest) {

        Advert advert = (Advert) dtoConverterService.dtoToEntity(advertRequest, Advert.class);
        advertDao.save(advert);
        return new SuccessResult("Status has been updated!");
    }
}
