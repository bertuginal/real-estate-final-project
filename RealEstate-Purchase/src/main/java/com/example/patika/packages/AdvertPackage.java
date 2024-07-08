package com.example.patika.packages;

import lombok.Getter;

@Getter
public class AdvertPackage {

    private int id = 1;
    private int numberOfAdvertRights = 10; // ilan yayınlama hak sayısı
    private int packageValidityPeriod = 30; // paket geçerlilik süresi (gün cinsinden)
    private Double price = 50.0; // paket ücreti


}
