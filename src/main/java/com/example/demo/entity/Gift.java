package com.example.demo.entity;

import jakarta.persistence.*;


@Entity
@Table(name="gifts")
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Максимум 256 символов, т.к. String - это Varchar(255)

    @Column(columnDefinition = "Text")
    private String description; // Максимум 65535 символов, благодаря columnDefinition = "Text"

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}