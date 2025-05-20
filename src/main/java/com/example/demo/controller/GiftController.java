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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller // аннотация spring, которая обрабатывает запросы от пользователей
public class GiftController { // класс управляет операциями с подарками (подарками в базе данных)

    private final GiftRepository giftRepository; // репозиторий для работы с подарками в базе данных
    private final UserRepository userRepository;

    public GiftController(GiftRepository giftRepository, UserRepository userRepository) { // конструктор для получения репозитория
        this.giftRepository = giftRepository; // сохраняем ссылку на репозиторий в поле класса
        this.userRepository = userRepository;
    }

    @GetMapping("/hello") // обрабатываем get-запрос на страницу "/hello"
    public String getHello(Model model) { // метод показывает приветственную страницу
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // получаем информацию о текущем пользователе
        if (auth != null) { // если пользователь авторизован
            model.addAttribute("username", auth.getName()); // добавляем имя пользователя в модель
        }
        return "hello"; // возвращаем html-шаблон для страницы приветствия
    }

    @GetMapping("/") // обрабатываем get-запрос на главную страницу
    public String showMainPage(){
        return "index";
    }
    @GetMapping("/gifts")
    public String showAllGifts(Model model, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        List<Gift> gifts = giftRepository.findByUser(user);
        model.addAttribute("gift", new Gift()); // добавляем пустой объект подарка в модель (для формы добавления)
        model.addAttribute("gifts", gifts); // добавляем список подарков в модель
        return "gifts-list"; // возвращаем html-шаблон для страницы со списком подарков
    }

    @GetMapping("/add") // обрабатываем get-запрос на страницу добавления подарка
    public String showAddGift(Model model) { // метод показывает форму добавления подарка
        model.addAttribute("gift", new Gift()); // добавляем пустой объект подарка в модель
        return "add-gift"; // возвращаем html-шаблон для страницы добавления подарка
    }

    @PostMapping("/add") // обрабатываем post-запрос при отправке формы добавления подарка
    @ResponseBody
    public String addGift(@ModelAttribute Gift gift, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        gift.setUser(user);
        giftRepository.save(gift); // сохраняем подарок в базе данных
        return "{\"result\": \"success\"}";
        //return "redirect:/gifts";
    }

    @GetMapping("/gift/{id}") // обрабатываем get-запрос на страницу просмотра конкретного подарка
    public String showSingleGift(@PathVariable Long id, Model model) { // метод показывает детали подарка
        Gift gift = giftRepository.findById(id).orElseThrow(); // находим подарок по id или выбрасываем ошибку, если его нет
        model.addAttribute("gift", gift); // добавляем подарок в модель
        return "gift-details"; // возвращаем html-шаблон для страницы с деталями подарка
    }

    @GetMapping("/edit/{id}") // обрабатываем get-запрос на страницу редактирования подарка
    public String showEditGift(@PathVariable Long id, Model model) { // метод показывает форму редактирования подарка
        Gift gift = giftRepository.findById(id).orElseThrow(); // находим подарок по id или выбрасываем ошибку, если его нет
        model.addAttribute("gift", gift); // добавляем подарок в модель
        return "edit-gift"; // возвращаем html-шаблон для страницы редактирования подарка
    }

    @PostMapping("/edit/{id}") // обрабатываем post-запрос при отправке формы редактирования подарка
    public String updateGift(@PathVariable Long id, @ModelAttribute Gift uploadedGift) { // метод обновляет данные подарка
        Gift gift = giftRepository.findById(id).orElseThrow(); // находим подарок по id или выбрасываем ошибку, если его нет
        gift.setName(uploadedGift.getName()); // обновляем название подарка
        gift.setDescription(uploadedGift.getDescription()); // обновляем описание подарка
        giftRepository.save(gift); // сохраняем обновленный подарок в базе данных
        return "redirect:/gift/" + id; // перенаправляем пользователя на страницу с деталями обновленного подарка
    }

    @GetMapping("/delete/{id}") // обрабатываем get-запрос на удаление подарка
    public String deleteGift(@PathVariable Long id) { // метод удаляет подарок
        giftRepository.deleteById(id); // удаляем подарок из базы данных по id
        return "redirect:/"; // перенаправляем пользователя на главную страницу
    }
}
