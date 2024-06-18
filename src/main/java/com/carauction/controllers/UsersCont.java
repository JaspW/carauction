package com.carauction.controllers;

import com.carauction.controllers.Main.Main;
import com.carauction.models.Users;
import com.carauction.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersCont extends Main {

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("role", getRole());
        return "users";
    }

    @PostMapping("/edit/{id}")
    public String userUpdate(@PathVariable Long id, @RequestParam Role role) {
        Users user = userRepo.getReferenceById(id);
        user.setRole(role);
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String userDelete(@PathVariable(value = "id") Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}
