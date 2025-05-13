package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity  // аннотация для spring
@Table(name="users")  // аннотация для БД таблица users
public class User {
    @Id  // аннотация идентификатор для каждого пользователя
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // аннотация увеличивает индификатор при добавлении пользователя
    private Long id;
    @Column(nullable = false, unique = true)  // аннотация для создания колонки с именем пользователя,
    // которая не должна быть пустой, так же имя пользователя не может повторяться
    private String username;
    @Column(nullable = false)  // аннотация для создания колонки пароль
    private String password;
    @Column(nullable = false)  // аннотация для создания колонки электронной почты
    private String email;

    // Связь с подарками (один пользователь - много подарков)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Gift> gifts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }
}