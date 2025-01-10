package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.app.services.DtoWrapper;
import com.example.mitfahrerzentrale.data.dtos.UserDTO;
import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ProfileController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    ProfileController(@Autowired UserRepo userRepo, @Autowired PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/profile")
    public String showProfile(Authentication auth, Model model) {
        User user = userRepo.findUserByName(auth.getName());
        if (user == null) {
            throw new IllegalStateException("Benutzer nicht gefunden: " + auth.getName());
        }

        System.out.println("Profil geladen für Benutzer: " + user.getName());
        model.addAttribute("user", DtoWrapper.userToDTO(user));

        return "show-profile";
    }

    @GetMapping("/edit-profile")
    public String showEditProfile(Authentication auth, Model model) {
        User user = userRepo.findUserByName(auth.getName());
        UserDTO userDTO = DtoWrapper.userToDTO(user);

        model.addAttribute("user", userDTO);

        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam(required = false) String name, @RequestParam(required = false) String email, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String password, @RequestParam(required = false) String confirmPassword, Authentication auth, RedirectAttributes redirectAttributes) {

        // Benutzer abrufen
        User user = userRepo.findUserByName(auth.getName());
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Benutzer nicht gefunden!");
            return "redirect:/profile";
        }

        // Name aktualisieren
        if (name != null && !name.isEmpty()) {
            user.setName(name);
        } else {
            redirectAttributes.addFlashAttribute("error", "Name nicht angegeben!");
            return "redirect:/profile";
        }

        // E-Mail aktualisieren
        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        } else {
            redirectAttributes.addFlashAttribute("error", "Email nicht angegeben!");
            return "redirect:/profile";
        }

        // Telefonnummer aktualisieren
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            user.setPhoneNumber(phoneNumber);
        } else {
            redirectAttributes.addFlashAttribute("error", "Phone number nicht angegeben!");
            return "redirect:/profile";
        }

        // Passwort prüfen und aktualisieren
        if (password != null && !password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Die Passwörter stimmen nicht überein!");
                return "redirect:/profile";
            }
            user.setPasswordHash(passwordEncoder.encode(password));
        } else {
            redirectAttributes.addFlashAttribute("error", "Password nicht angegeben!");
        }

        // Benutzer speichern
        userRepo.save(user);

        // SecurityContext aktualisieren (bestehende Logik bleibt erhalten)
        Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getName(), auth.getCredentials(), auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // Weiterleitung zur Profilseite
        return "redirect:/profile";
    }

}
