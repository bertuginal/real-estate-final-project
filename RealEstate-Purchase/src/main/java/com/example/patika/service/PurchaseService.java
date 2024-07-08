package com.example.patika.service;


import com.example.patika.dto.request.PurchaseRequest;
import com.example.patika.dto.request.PurchaseStatusRequest;
import com.example.patika.dto.response.PurchaseResponse;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;

import java.util.List;

public interface PurchaseService {

    DataResult<List<PurchaseResponse>> findAll();
    Result purchase(PurchaseRequest purchaseRequest,int userId);
    Result updatePurchaseStatusById(int purchaseId, PurchaseStatusRequest purchaseStatusRequest);
    DataResult<PurchaseResponse> findById(int purchaseId);
    DataResult<List<PurchaseResponse>> findAllByUserId(int userId);
}
