package com.carauction.controllers;

import com.carauction.controllers.Main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
public class MyCont extends Main {
    @GetMapping
    public String my(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        return "my";
    }
}