package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class ProfileController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;


    @GetMapping("/profile")
    public String showProfile() {
        return "profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestBody Map<String, String> body, Authentication auth) {
        User user = userRepo.findUserByName(auth.getName());
        for (Map.Entry<String, String> entry : body.entrySet()) {
            switch (entry.getKey()) {
                case "username":
                    user.setName(entry.getValue());
                case "password":
                    user.setPasswordHash(passwordEncoder.encode(entry.getValue()));
                case "email":
                    user.setEmail(entry.getValue());
                case "phonenumber":
                    user.setPhoneNumber(entry.getValue());
            }
            userRepo.saveAndFlush(user);
        }
        return "redirect:/profile";
    }

}
