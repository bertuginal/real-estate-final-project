package com.example.patika.repository;


import com.example.patika.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertDao extends JpaRepository<Advert, Integer> {


}