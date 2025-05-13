package com.example.demo.config;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // аннотация spring конфигурационный класс
@EnableWebSecurity  // включает настройку безопасности в приложении
public class SecurityConfig {  // класс, который настраивает безопасность приложения
    private final CustomUserDetailsService userDetailsService;  // ссылка на сервис, который ищет пользователей

    public SecurityConfig(CustomUserDetailsService userDetailsService) {   // конструктор для получения сервиса пользователей
        this.userDetailsService = userDetailsService;  // сохраняем ссылку на сервис в поле класса
    }

    @Bean  // аннотация spring, создает важный объект для работы приложения
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{  // метод настраивает правила безопасности
        http
                .authorizeHttpRequests(auth-> auth  // начинаем настраивать,
                        // какие запросы разрешены или запрещены
                        .requestMatchers("/", "/registration", "/login").permitAll()  // разрешаем всем доступ
                        // к главной странице, регистрации и входу
                        .anyRequest().authenticated()  // для всех остальных запросов требуется аутентификация
                ).formLogin(form->form.loginPage("/login").defaultSuccessUrl("/profile").permitAll())
                // настраиваем форму для входа, указываем, что страница входа находится по адресу "/login",
                // после успешного входа перенаправляем на страницу "/profile", разрешаем всем доступ к форме входа
                .logout(logout->logout.logoutSuccessUrl("/").permitAll())  // настраиваем выход
                // из системы, после выхода перенаправляем на главную страницу ("/"), разрешаем всем доступ к функции выхода
                .userDetailsService(userDetailsService);  // используем наш сервис для поиска пользователей
        return http.build();  // создаем и возвращаем цепочку правил безопасности
    }
    @Bean
    public PasswordEncoder passwordEncoder(){  // метод настраивает способ шифрования паролей
        return new BCryptPasswordEncoder();  // используем алгоритм BCrypt для безопасного хранения паролей
    }
}