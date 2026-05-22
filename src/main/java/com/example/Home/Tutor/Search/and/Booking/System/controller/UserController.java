package com.example.Home.Tutor.Search.and.Booking.System.controller;

import com.example.Home.Tutor.Search.and.Booking.System.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        user.setId(nextId++);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                model.addAttribute("user", user);
                return "edit-user";
            }
        }
        return "redirect:/users";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User updatedUser) {
        for (User user : users) {
            if (user.getId().equals(updatedUser.getId())) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setPassword(updatedUser.getPassword());
                break;
            }
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
        return "redirect:/users";
    }
}