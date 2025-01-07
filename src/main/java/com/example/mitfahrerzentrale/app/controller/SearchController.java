package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.geo.GeocodeController;
import com.example.mitfahrerzentrale.geo.dto.NominatimResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final RideRepo rideRepo;

    @Autowired
    GeocodeController geocodeController;

    public SearchController(RideRepo rideRepo) {
        this.rideRepo = rideRepo;
    }

    @GetMapping("/search")
    public String search(@RequestParam String departureLocation, Model model) {
        try {
            // Abrufen der Suchergebnisse vom GeocodeController (Service)
            List<NominatimResponseDTO> searchResults = geocodeController.searchLocation(departureLocation);

            // Prüfe, ob Suchergebnisse vorhanden sind, und füge sie dem Model hinzu
            if (searchResults != null && !searchResults.isEmpty()) {
                model.addAttribute("searchResults", searchResults);
            } else {
                model.addAttribute("message", "Keine Suchergebnisse gefunden.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Ein Fehler ist aufgetreten: " + e.getMessage());
        }

        // Gib den Namen der View zurück, die die Suchergebnisse rendern soll
        return "suchergebnisse";
    }
}
