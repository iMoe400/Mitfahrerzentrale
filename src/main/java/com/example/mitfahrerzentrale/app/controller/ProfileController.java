package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ProfileController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RideRepo rideRepo;


    @GetMapping("/profile")
    public String showProfile(Authentication auth, Model model) {
        User user =userRepo.findUserByName(auth.getName());
        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(Authentication auth) {
        User user = userRepo.findUserByName(auth.getName());

        return "redirect:/profile";
    }

}
