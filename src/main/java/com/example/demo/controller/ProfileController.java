package com.example.demo.controller;

import com.example.demo.entity.Gift;
import com.example.demo.entity.User;
import com.example.demo.repository.GiftRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller // аннотация spring, которая обрабатывает запросы от пользователей
public class ProfileController { // класс управляет операциями с профилем пользователя

    private final UserRepository userRepository; // репозиторий для работы с пользователями в базе данных
    private final GiftRepository giftRepository; // репозиторий для работы с подарками в базе данных

    public ProfileController(UserRepository userRepository, GiftRepository giftRepository) { // конструктор для получения репозиториев
        this.userRepository = userRepository; // сохраняем ссылку на репозиторий пользователей
        this.giftRepository = giftRepository; // сохраняем ссылку на репозиторий подарков
    }

    @GetMapping("/profile") // обрабатываем get-запрос на страницу профиля
    public String showProfile(Model model) { // метод показывает страницу профиля
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // получаем информацию о текущем пользователе
        String username = auth.getName(); // получаем имя пользователя
        User user = userRepository.findByUsername(username); // находим пользователя по имени
        List<Gift> gifts = giftRepository.findByUser(user); // получаем список подарков пользователя
        model.addAttribute("user", user); // добавляем пользователя в модель
        model.addAttribute("gifts", gifts); // добавляем список подарков в модель
        return "profile"; // возвращаем html-шаблон для страницы профиля
    }
}