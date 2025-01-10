package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.app.services.RideService;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DeleteController {

    @Autowired
    private final RideService rideService;
    @Autowired
    private UserRepo userRepo;

    public DeleteController(RideService rideService) {
        this.rideService = rideService;
    }

    /**
     * Löscht eine angebotene Fahrt.
     *
     * @param rideId             Die ID der zu löschenden Fahrt.
     * @param authentication     Die aktuelle Authentifizierung des Nutzers.
     * @param redirectAttributes Redirect-Attribute für Statusmeldungen.
     * @return Weiterleitung zur Startseite.
     */
    @PostMapping("delete-ride")
    public String deleteOfferedRide(@RequestParam("rideId") Integer rideId, Authentication authentication, RedirectAttributes redirectAttributes) {


        // Überprüfen Sie, ob der Benutzer authentifiziert ist
        if (authentication == null || authentication.getPrincipal() == null) {
            redirectAttributes.addFlashAttribute("error", "Fehler: Benutzer ist nicht angemeldet.");
            return "redirect:/login"; // Oder eine andere geeignete Route
        }

        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
            rideService.deleteOfferedRide(rideId, userRepo.findUserByName(authentication.getName()).getId());
        } else {
            try {
                rideService.deleteOfferedRide(rideId, userRepo.findUserByName(authentication.getName()).getId());
                redirectAttributes.addFlashAttribute("success", "Die Fahrt wurde erfolgreich gelöscht.");
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("error", "Fehler: " + e.getMessage());
            }
        }
        return "redirect:/home";
    }

    /**
     * Löscht eine Buchung.
     *
     * @param bookingId          Die ID der zu löschenden Buchung.
     * @param rideId             Die ID der Fahrt, der die Buchung zugeordnet ist.
     * @param redirectAttributes Redirect-Attribute für Statusmeldungen.
     * @return Weiterleitung zur Startseite.
     */
    @PostMapping("/delete-booking")
    public String deleteBookedRide(@RequestParam("bookingId") Integer bookingId, @RequestParam("rideId") Integer rideId, RedirectAttributes redirectAttributes) {
        try {
            rideService.deleteBookedRide(bookingId, rideId);
            redirectAttributes.addFlashAttribute("success", "Die Buchung wurde erfolgreich storniert.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Fehler: " + e.getMessage());
        }
        return "redirect:/home";
    }
}
