package com.example.patika.repository;


import com.example.patika.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDao extends JpaRepository<Payment, Integer> {

    Payment findById(int id);

}