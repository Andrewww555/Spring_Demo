package com.example.demo.entity;

import jakarta.persistence.*;


@Entity // аннотация spring, сущность базы данных
@Table(name="gifts") // указываем имя таблицы в базе данных
public class Gift { // класс представляет таблицу подарков в базе данных

    @Id // аннотация, первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id будет автоматически генерироваться базой данных
    private Long id; // уникальный идентификатор подарка

    @Column(nullable = false) // поле не может быть пустым
    private String name; // название подарка максимум 256 символов

    @Column(columnDefinition = "Text") // поле может хранить текст большого размера
    private String description; // описание подарка максимум 65535 символов

    @ManyToOne // аннотация, где много подарков принадлежат одному пользователю
    @JoinColumn(name = "user_id") // объединяем таблицы по колонке user_id
    private User user; // ссылка на пользователя, которому принадлежит подарок

    public void setId(Long id) { // метод для установки значения id
        this.id = id;
    }

    public void setName(String name) { // метод для установки названия подарка
        this.name = name;
    }

    public void setDescription(String description) { // метод для установки описания подарка
        this.description = description;
    }

    public Long getId() { // метод для получения id подарка
        return id;
    }

    public String getName() { // метод для получения названия подарка
        return name;
    }

    public String getDescription() { // метод для получения описания подарка
        return description;
    }  // метод для получения описания подарка
}