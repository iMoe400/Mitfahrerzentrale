package com.example.mitfahrerzentrale.app.controller;


import com.example.mitfahrerzentrale.app.services.HomeService;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final UserRepo userRepo;
    private final HomeService homeService;

    public HomeController(@Autowired UserRepo userRepo, @Autowired HomeService homeService) {
        this.userRepo = userRepo;
        this.homeService = homeService;
    }
//Startseite Inhalt:
    //th:if="${fahrten}" -> div für die fahrten über suchleiste und ergebnissen
    //ansonsnten einfach nur suchleiste und ergebnisse, ggf vorläufige ergebnisse wenn Standort aktiviert
    //User nach Standort bei Anmeldung fragen?
    //User einem Wohnort zuordnen?


    @GetMapping("/home")
    public String home(Authentication authentication, Model model, String locationData, String locationLat, String locationLon, String searchType, String departureData, String departureLat, String departureLon, String destinationData, String destinationLat, String destinationLon) {

        // Rolle Admin
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
            homeService.prepareAdminModel(userRepo.findUserByName(authentication.getName()), model);
        } else {
            // Rolle User
            homeService.prepareUserModel(userRepo.findUserByName(authentication.getName()), model, locationData, locationLat, locationLon, searchType, departureData, departureLat, departureLon, destinationData, destinationLat, destinationLon);
        }

        return "home";
    }


}
