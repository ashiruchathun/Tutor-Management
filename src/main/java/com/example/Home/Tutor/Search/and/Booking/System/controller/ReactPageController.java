package com.example.Home.Tutor.Search.and.Booking.System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactPageController {

    // Serve the React dashboard from the requested backend URL.
    @GetMapping({"/", "/tutors", "/tutors/add", "/tutors/edit/{id}"})
    public String dashboard() {
        return "forward:/index.html";
    }
}
