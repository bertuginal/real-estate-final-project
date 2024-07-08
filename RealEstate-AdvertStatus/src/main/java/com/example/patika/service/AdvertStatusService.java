package com.example.patika.service;

import com.example.patika.dto.request.AdvertStatusRequest;
import com.example.patika.utils.results.Result;

public interface AdvertStatusService {
    Result updateStatus(AdvertStatusRequest advertRequest);
}
