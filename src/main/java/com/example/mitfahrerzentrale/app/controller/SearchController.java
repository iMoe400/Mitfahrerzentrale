package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.geo.GeocodeController;
import com.example.mitfahrerzentrale.geo.dto.NominatimResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class SearchController {


    private final GeocodeController geocodeController;


    public SearchController(@Autowired GeocodeController geocodeController) {
        this.geocodeController = geocodeController;
    }

    @GetMapping("/search")
    public String search(
            @RequestParam String location,
            @RequestParam String searchType,
            @RequestParam(required = false) String departureData,
            @RequestParam(required = false) String departureLat,
            @RequestParam(required = false) String departureLon,
            @RequestParam(required = false) String destinationData,
            @RequestParam(required = false) String destinationLat,
            @RequestParam(required = false) String destinationLon,
            Model model, RedirectAttributes redirectAttributes) {
        try {
            List<NominatimResponseDTO> searchResults = geocodeController.searchLocation(location);
            if(location == null) {
                redirectAttributes.addFlashAttribute("error", "Ohne Angaben, keine Ergebnisse");
                return "redirect:/home";
            }
            if(!location.isEmpty()) {
                model.addAttribute("searchResults", searchResults);
                model.addAttribute("searchType", searchType);
                model.addAttribute("departureData", departureData);
                model.addAttribute("departureLat", departureLat);
                model.addAttribute("departureLon", departureLon);
                model.addAttribute("destinationData", destinationData);
                model.addAttribute("destinationLat", destinationLat);
                model.addAttribute("destinationLon", destinationLon);
            } else {
                redirectAttributes.addFlashAttribute("error", "Es gab keine Ergebnisse für ihre Suche. Bitte versuchen Sie den Ort genauer anzugeben");
                return "redirect:/home";
            }



        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        return "suchergebnisse";
    }
}
