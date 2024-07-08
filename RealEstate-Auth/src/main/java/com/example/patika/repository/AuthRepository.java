package com.example.patika.repository;


import com.example.patika.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}
