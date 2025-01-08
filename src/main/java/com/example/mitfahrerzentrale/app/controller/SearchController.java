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
    public String search(@RequestParam String location,
                         @RequestParam String searchType,
                         @RequestParam(required = false) String departureData,
                         @RequestParam(required = false) String destinationData,
                         Model model) {
        try {
            List<NominatimResponseDTO> searchResults = geocodeController.searchLocation(location);

            model.addAttribute("searchResults", searchResults);
            model.addAttribute("searchType", searchType);
            model.addAttribute("departureData", departureData);
            model.addAttribute("destinationData", destinationData);

        } catch (Exception e) {
            model.addAttribute("message", "Ein Fehler ist aufgetreten: " + e.getMessage());
        }

        return "suchergebnisse";
    }

}
