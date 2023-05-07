package com.adnan13.holidayapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String welcome() {
        return "Welcome to Holiday API";
    }
}
