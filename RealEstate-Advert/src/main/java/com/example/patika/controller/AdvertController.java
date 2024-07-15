package com.example.patika.controller;

import com.example.patika.dto.request.AdvertRequest;
import com.example.patika.dto.request.AdvertUpdateRequest;
import com.example.patika.dto.response.AdvertResponse;
import com.example.patika.service.AdvertService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdvertController {


    private AdvertService advertService;


    @Autowired
    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    // Tüm ilanların listelenmesi işlemi
    @GetMapping("/adverts")
    public ResponseEntity<DataResult<List<AdvertResponse>>> findAll() {
        DataResult<List<AdvertResponse>> result= advertService.findAll();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // Login olan kullanıcının id'ye göre ilan ekleme işlemi
    @PostMapping("/adverts")
    public ResponseEntity<Result> add(@RequestBody AdvertRequest advertRequest,@RequestHeader(value = "id") int id) {
        Result result = advertService.add(advertRequest,id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // İlanın id'sine göre güncelleme işlemi
    @PutMapping("/adverts/{id}")
    public ResponseEntity<Result> updateById(@PathVariable int id, @RequestBody AdvertUpdateRequest advertUpdateRequest){
        Result result = advertService.updateById(id,advertUpdateRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // İlanın id'sine göre silme işlemi
    @DeleteMapping("/adverts/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable int id){
        Result result = advertService.deleteById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // İlanın id'sine göre ilan listeleme işlemi
    @GetMapping("/adverts/{id}")
    public ResponseEntity<DataResult<AdvertResponse>> findById(@PathVariable int id) {
        DataResult<AdvertResponse> result = advertService.findById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // Kullanıcının id'sine göre aktif ilanları listeleme işlemi
    @GetMapping("/adverts/actives/individual-user/{userId}")
    public ResponseEntity<DataResult<List<AdvertResponse>>> findAllByUserIdAndIsActive(  @PathVariable int userId) {
        DataResult<List<AdvertResponse>> result= advertService.findAllByUserIdAndIsActive(userId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // Kullanıcının id'sine göre pasif ilanları listeleme işlemi
    @GetMapping("/adverts/passives/individual-user/{userId}")
    public ResponseEntity<DataResult<List<AdvertResponse>>> findAllByUserIdAndIsPassive( @PathVariable  int userId) {

        DataResult<List<AdvertResponse>> result= advertService.findAllByUserIdAndIsPassive(userId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // İlanın id'sine göre ilan statüsünü aktif duruma getirme işlemi
    @PutMapping("/adverts/update-status-active/{id}")
    public ResponseEntity<Result> updateStatusActiveById(@PathVariable int id){
        Result result = advertService.updateStatusActiveById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // İlanın id'sine göre ilan statüsünü pasif duruma getirme işlemi
    @PutMapping("/adverts/update-status-passive/{id}")
    public ResponseEntity<Result> updateStatusPassiveById(@PathVariable int id){
        Result result = advertService.updateStatusPassiveById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }


}
