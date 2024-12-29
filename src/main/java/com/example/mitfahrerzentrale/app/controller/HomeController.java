package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class HomeController {


    public HomeController() {

    }

    @GetMapping("/home")
    public String home(Authentication authentication, RedirectAttributes redirectAttributes, Model model) {

        if (!authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "You are not logged in");
            return "redirect:/login";
        }
        return "home";
    }

}
