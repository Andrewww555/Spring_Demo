package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {  // создаём интерфейс репозиторий,
    // наследуемся от JpaRepository, передаём класс User и поле Long
    User findByUsername(String username);  // метод по поиску имени пользователя
    User findByEmail(String email);  // метод по поиску электронной почты
}