package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.app.services.UserService;
import com.example.mitfahrerzentrale.data.dtos.UserDTO;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    private final UserRepo userRepo;

    public HomeController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
//Startseite Inhalt:
    //th:if="${fahrten}" -> div für die fahrten über suchleiste und ergebnissen
    //ansonsnten einfach nur suchleiste und ergebnisse, ggf vorläufige ergebnisse wenn Standort aktiviert
    //User nach Standort bei Anmeldung fragen?
    //User einem Wohnort zuordnen?


    @GetMapping("/home")
    public String home(Authentication authentication, RedirectAttributes redirectAttributes, Model model) {

        UserDTO userDTO = UserService.userToDTO(userRepo.findUserByName(authentication.getName()));
        model.addAttribute("user", userDTO);
        return "home";
    }

}
