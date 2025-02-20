package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class SearchRidesController {

    private final RideRepo rideRepo;
    private final UserRepo userRepo;

    @Autowired
    public SearchRidesController(RideRepo rideRepo, UserRepo userRepo) {
        this.rideRepo = rideRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/search-rides")
    public String searchRides(@RequestParam(required = false) String startQuery, @RequestParam(required = false) String destinationQuery, Authentication authentication, RedirectAttributes redirectAttributes) {
        // Wenn beide Felder leer sind, Fehler anzeigen
        if ((startQuery == null || startQuery.isEmpty()) && (destinationQuery == null || destinationQuery.isEmpty())) {
            redirectAttributes.addFlashAttribute("error", "Gebe bitte einen Start- oder Zielort an!");
            return "redirect:/home";
        }
        // Aktuellen Nutzer abrufen
        User currentUser = userRepo.findUserByName(authentication.getName());
        Integer currentUserId = currentUser.getId();

        Optional<List<Ride>> filteredRides;

        filteredRides = rideRepo.findByStartLocationContainingIgnoreCaseAndDestinationLocationContainingIgnoreCaseAndDriverIdNot(startQuery, destinationQuery, currentUserId);

        // Nur Fahrten, die nicht vom aktuellen Nutzer erstellt wurden, hinzufügen
        if (filteredRides.isPresent() && !filteredRides.get().isEmpty()) {
            redirectAttributes.addFlashAttribute("rides", filteredRides.get());
        } else {
            redirectAttributes.addFlashAttribute("rides", List.of());
        }

        return "redirect:/home";
    }
}