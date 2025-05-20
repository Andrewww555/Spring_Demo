package com.example.demo.controller;

import com.example.demo.entity.Gift;
import com.example.demo.entity.User;
import com.example.demo.repository.GiftRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller // аннотация spring, которая обрабатывает запросы от пользователей
public class ProfileController { // класс управляет операциями с профилем пользователя

    private final UserRepository userRepository; // репозиторий для работы с пользователями в базе данных
    private final GiftRepository giftRepository; // репозиторий для работы с подарками в базе данных
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserRepository userRepository, GiftRepository giftRepository, PasswordEncoder passwordEncoder) { // конструктор для получения репозиториев
        this.userRepository = userRepository; // сохраняем ссылку на репозиторий пользователей
        this.giftRepository = giftRepository; // сохраняем ссылку на репозиторий подарков
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile") // обрабатываем get-запрос на страницу профиля
    public String showProfile(Model model) { // метод показывает страницу профиля
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // получаем информацию о текущем пользователе
        if(auth != null){
            model.addAttribute("username", auth.getName());
        }
        String username = auth.getName(); // получаем имя пользователя
        User user = userRepository.findByUsername(username); // находим пользователя по имени
        List<Gift> gifts = giftRepository.findByUser(user); // получаем список подарков пользователя
        model.addAttribute("user", user); // добавляем пользователя в модель
        model.addAttribute("gifts", gifts); // добавляем список подарков в модель
        return "profile"; // возвращаем html-шаблон для страницы профиля
    }

    @GetMapping("/profile/edit") // обрабатываем get-запрос на страницу редактирования профиля
    public String showEditProfile(Model model) { // метод показывает форму редактирования профиля
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // получаем информацию о текущем пользователе
        String username = auth.getName(); // получаем имя пользователя
        User user = userRepository.findByUsername(username); // находим пользователя по имени
        model.addAttribute("user", user); // добавляем пользователя в модель
        return "edit-profile"; // возвращаем html-шаблон для страницы редактирования профиля
    }

    @PostMapping("/profile/edit") // обрабатываем post-запрос при отправке формы редактирования профиля
    public String updateProfile(@ModelAttribute User updatedUser) { // метод обновляет данные пользователя
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // получаем информацию о текущем пользователе
        String username = auth.getName(); // получаем имя пользователя
        User user = userRepository.findByUsername(username); // находим пользователя по имени
        user.setUsername(updatedUser.getUsername()); // обновляем имя пользователя
        user.setPassword(updatedUser.getPassword()); // обновляем пароль пользователя
        userRepository.save(user); // сохраняем обновленные данные в базе данных
        return "redirect:/profile"; // перенаправляем пользователя на страницу профиля
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String password, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "redirect:/profile";
    }
}