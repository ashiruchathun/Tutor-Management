package com.example.Home.Tutor.Search.and.Booking.System.controller;

import com.example.Home.Tutor.Search.and.Booking.System.model.User;
import com.example.Home.Tutor.Search.and.Booking.System.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return "redirect:/users";
        }

        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User updatedUser) {
        userRepository.save(updatedUser);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}