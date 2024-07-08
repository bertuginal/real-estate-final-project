package com.example.patika.service;

import com.example.patika.dto.request.AdvertRequest;
import com.example.patika.dto.request.AdvertUpdateRequest;
import com.example.patika.dto.response.AdvertResponse;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;

import java.util.List;

public interface AdvertService {
    DataResult<List<AdvertResponse>> findAll();
    Result add(AdvertRequest advertRequest,int id);
    Result updateById(int advertId, AdvertUpdateRequest advertUpdateRequest);
    Result deleteById(int advertId);
    DataResult<AdvertResponse> findById(int advertId);
    DataResult<List<AdvertResponse>> findAllByUserIdAndIsActive(int userId);
    DataResult<List<AdvertResponse>> findAllByUserIdAndIsPassive(int userId);
    Result updateStatusActiveById(int advertId);
    Result updateStatusPassiveById(int advertId);

}
