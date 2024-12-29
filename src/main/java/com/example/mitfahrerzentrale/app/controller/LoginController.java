package com.example.mitfahrerzentrale.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@ModelAttribute("username") String username, @ModelAttribute("password") String password, @ModelAttribute("error") String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        return "login";
    }
}
