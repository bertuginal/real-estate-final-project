package com.example.patika.packages;

import lombok.Getter;

@Getter
public class AdvertPackage {

    private int id = 1;
    private int numberOfAdvertRights = 10; // İlan yayınlama hakkı
    private int packageValidityPeriod = 30; // Paket geçerlilik süresi (gün)
    private Double price = 50.0; // Paket ücreti


}
