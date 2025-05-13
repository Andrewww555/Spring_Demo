package com.example.demo.repository;

import com.example.demo.entity.Gift;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    List<Gift> findByUser(User user); // метод для поиска подарков по пользователю
}