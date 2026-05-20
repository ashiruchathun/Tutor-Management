package com.example.Home.Tutor.Search.and.Booking.System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactPageController {

    // Serve React routes from Spring Boot so direct browser refreshes keep working.
    @GetMapping({"/", "/tutors", "/admin/tutors", "/admin/tutors/add", "/admin/tutors/edit/{id}"})
    public String dashboard() {
        return "forward:/index.html";
    }
}
