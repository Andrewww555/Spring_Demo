package com.example.demo.controller;

import com.example.demo.entity.Gift;
import com.example.demo.repository.GiftRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class GiftController {
    private final GiftRepository giftRepository;

    public GiftController(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }
    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }
    @GetMapping("/")
    public String showAllGifts(Model model){
        List<Gift> gifts = giftRepository.findAll();
        model.addAttribute("gift", new Gift());
        model.addAttribute("gifts", gifts);
        return "gifts-list";
    }
    @GetMapping("/add")
    public String showAddGift(Model model){
        model.addAttribute("gift", new Gift());
        return "add-gift";
    }
    @PostMapping("/add")
    public String addGift(@ModelAttribute Gift gift){
        giftRepository.save(gift);
        return "redirect:/";
    }
    @GetMapping("/gift/{id}")
    public String showSingleGift(@PathVariable Long id, Model model){
        Gift gift = giftRepository.findById(id).orElseThrow();
        model.addAttribute("gift", gift);
        return "gift-details";
    }
    @GetMapping("/edit/{id}")
    public String showEditGift(@PathVariable Long id, Model model){
        Gift gift = giftRepository.findById(id).orElseThrow();
        model.addAttribute("gift", gift);
        return "edit-gift";
    }
    @PostMapping("/edit/{id}")
    public String updateGift(@PathVariable Long id, @ModelAttribute Gift uploadedGift){
        Gift gift = giftRepository.findById(id).orElseThrow();
        gift.setName(uploadedGift.getName());
        gift.setDescription(uploadedGift.getDescription());
        giftRepository.save(gift);
        return "redirect:/gift/"+id;
    }
    @GetMapping("/delete/{id}")
    public String deleteGift(@PathVariable Long id){
        giftRepository.deleteById(id);
        return "redirect:/";
    }
}
