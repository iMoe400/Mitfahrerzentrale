package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    RegisterController(@Autowired UserRepo userRepo, @Autowired PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping("/register/create")
    public String confirmRegisterForm(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "phone_number", required = false) String phoneNumber, @RequestParam(value = "password", required = false) String password, RedirectAttributes redirectAttributes) {

        try {
            if (userRepo.existsByEmail(email) || userRepo.existsByName(username) || userRepo.existsByPhoneNumber(phoneNumber)) {
                redirectAttributes.addFlashAttribute("error", "User already exists");
                return "redirect:/register";
            }
            User user = new User();

            String hashedPassword = passwordEncoder.encode(password);

            user.setEmail(email);
            user.setName(username);
            user.setPhoneNumber(phoneNumber);
            user.setPasswordHash(hashedPassword);
            user.setIsActive(true);
            user.setRole("USER");
            userRepo.saveAndFlush(user);
            redirectAttributes.addAttribute("username", username);
            redirectAttributes.addAttribute("password", password);
        } catch (Exception e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }
}
