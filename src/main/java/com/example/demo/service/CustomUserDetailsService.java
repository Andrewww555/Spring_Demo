package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service  // аннотация spring, указывает, что это сервисный компонент
public class CustomUserDetailsService implements UserDetailsService {  // класс реализует интерфейс UserDetailsService
    private final UserRepository userRepository;  // репозиторий для работы с пользователями в базе данных

    public CustomUserDetailsService(UserRepository userRepository) {  // конструктор для внедрения зависимости
        this.userRepository = userRepository;  // сохраняем репозиторий в поле класса
    }

    @Override  // переопределяем метод из интерфейса UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  // метод загружает
        // пользователя по имени
        System.out.println("Вызван loadUserByUsername с параметром " + username);  // отладочное сообщение в консоль
        User user = userRepository.findByUsername(username);  // ищем пользователя в базе данных по имени
        if(user == null){  // если пользователь не найден
            throw new UsernameNotFoundException("User not found");  // выбрасываем исключение
        }
        return new org.springframework.security.core.userdetails.User(  // создаем объект UserDetails
                user.getUsername(),  // имя пользователя
                user.getPassword(),  // пароль пользователя
                Collections.emptyList());  // пустой список
    }
}