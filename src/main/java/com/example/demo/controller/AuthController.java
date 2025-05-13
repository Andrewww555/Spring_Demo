package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller  // аннотация spring, которая обрабатывает запросы от пользователей
public class AuthController {  // класс, который управляет регистрацией и входом пользователей
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {  // конструктор для получения зависимостей
        this.userRepository = userRepository;  // сохраняем ссылку на репозиторий пользователей
        this.passwordEncoder = passwordEncoder;  // сохраняем ссылку на кодировщик паролей
    }

    private final UserRepository userRepository;  // репозиторий для работы с пользователями в базе данных
    private final PasswordEncoder passwordEncoder;  // инструмент для шифрования паролей

    @GetMapping("/registration")  // обрабатываем get-запрос на страницу регистрации
    public String registration(Model model){  // метод показывает форму регистрации
        model.addAttribute("user", new User());  // добавляем пустого пользователя в модель для формы
        return "registration";  // возвращаем html-шаблон для страницы регистрации
    }

    @PostMapping("/registration")  // обрабатываем post-запрос при отправке формы регистрации
    public String registerUser(@Valid User user, BindingResult result, Model model){  // Метод сохраняет нового пользователя
        if(result.hasErrors()){  // если есть ошибки в заполнении
            return "registration";  // показываем форму регистрации снова
        }
        if(userRepository.findByUsername(user.getUsername()) != null){  // если пользователь с таким именем уже существует
            model.addAttribute("error", "Username already exist");  // добавляем сообщение
            // об ошибке в модель
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // шифруем пароль пользователя перед сохранением
        userRepository.save(user);  // сохраняем пользователя в базе данных
        return "redirect: /login";  // перенаправляем пользователя на страницу входа
    }
    @GetMapping("/login")  // обрабатываем get-запрос на страницу входа
    public String login(){
        return "login";
    }  // метод показывает форму входа и возвращает на html-шаблон входа
}