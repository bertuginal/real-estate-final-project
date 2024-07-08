package com.example.patika.repository;

import com.example.patika.model.IndividualUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividiualUserDao extends JpaRepository<IndividualUser, Integer>{

    IndividualUser findById(int id);

}
