package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.app.services.DtoWrapper;
import com.example.mitfahrerzentrale.data.dtos.UserDTO;
import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Objects;

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
        UserDTO userDTO = DtoWrapper.userToDTO(user);

        model.addAttribute("user", userDTO);

        return "show-profile";
    }

    @GetMapping("/edit-profile")
    public String showEditProfile(Authentication auth, Model model) {
        User user =userRepo.findUserByName(auth.getName());
        UserDTO userDTO = DtoWrapper.userToDTO(user);

        model.addAttribute("user", userDTO);

        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String telefonnummer,
                              @RequestParam(required = false) String password,
                              @RequestParam(required = false) String passwordConfirm,
                              Authentication auth, Model model) {
        User user = userRepo.findUserByName(auth.getName());
        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }
        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }
        if (telefonnummer != null && !telefonnummer.isEmpty()) {
            user.setPhoneNumber(telefonnummer);
        }
        if (password != null && !password.isEmpty()) {
            if (!Objects.equals(password, passwordConfirm)) {
                model.addAttribute("error", "Password stimmt nicht überein");
                return "edit-profile";
            }
            user.setPasswordHash(passwordEncoder.encode(password));
        }

        // Benutzer speichern
        userRepo.save(user);

        // SecurityContext aktualisieren
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                user.getName(),  // Neuer Benutzername
                auth.getCredentials(), // Aktuelles Passwort
                auth.getAuthorities()  // Bestehende Berechtigungen
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/profile";
    }
}
