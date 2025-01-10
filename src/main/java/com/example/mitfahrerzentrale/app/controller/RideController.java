package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import com.example.mitfahrerzentrale.geo.calculation.Haversine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Controller
public class RideController {

    private final RideRepo rideRepo;
    private final UserRepo userRepo;

    RideController(@Autowired RideRepo rideRepo, @Autowired UserRepo userRepo) {
        this.rideRepo = rideRepo;
        this.userRepo = userRepo;
    }


    @PostMapping("/create-ride")
    public String createRide(Authentication authentication, @RequestParam(value = "maxPassengers", required = false) String maxPassengers, @RequestParam(value = "departureLocation", required = false) String departureLocation, @RequestParam(value = "destinationLocation", required = false) String destinationLocation, @RequestParam(value = "departureTime", required = false) LocalDateTime departureTime, @RequestParam(value = "departureLat", required = false) String departureLat, @RequestParam(value = "departureLon", required = false) String departureLon, @RequestParam(value = "destinationLat", required = false) String destinationLat, @RequestParam(value = "destinationLon", required = false) String destinationLon, RedirectAttributes redirectAttributes) {

        if (departureLocation == null || destinationLocation == null || departureLat == null || departureLon == null || destinationLat == null || destinationLon == null || maxPassengers == null || departureTime == null) {
            redirectAttributes.addFlashAttribute("error", "Alle Felder müssen ausgefüllt sein.");
            return "redirect:/home";
        }

        if (departureTime.isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error", "Zeitreisen ist NOOCH nicht möglich!");
            return "redirect:/home";
        }

        if (Double.parseDouble(maxPassengers) <= 0) {
            redirectAttributes.addFlashAttribute("error", "Warum willst du eine Fahrt anbieten, wenn du doch niemanden Mitnimmst? ;)");
            return "redirect:/home";
        }

        if(Double.parseDouble(maxPassengers) >8){
            redirectAttributes.addFlashAttribute("error", "Maximale Passagierzahl darf maximal 8 sein.");
            return "redirect:/home";
        }

        // Berechnung des Zeitfensters (30 Minuten vor und nach der angegebenen Zeit)
        Instant startTime = departureTime.atZone(ZoneId.of("Europe/Berlin")).toInstant();
        Instant startWindow = startTime.minusSeconds(30 * 60); // 30 Minuten vorher
        Instant endWindow = startTime.plusSeconds(30 * 60);    // 30 Minuten nachher

        // Aktueller Benutzer
        User driver = userRepo.findUserByName(authentication.getName());

        // Prüfung, ob bereits eine Fahrt mit demselben Start und Zielpunkt im Zeitfenster existiert
        boolean exists = rideRepo.existsRideWithinTwoHours(driver, startWindow, endWindow);

        if (exists) {
            redirectAttributes.addFlashAttribute("error", "Eine ähnliche Fahrt existiert bereits innerhalb des Zeitfensters.");
            return "redirect:/home";
        }

        // Neue Fahrt erstellen
        double destLat = Double.parseDouble(destinationLat);
        double destLon = Double.parseDouble(destinationLon);
        double startLat = Double.parseDouble(departureLat);
        double startLon = Double.parseDouble(departureLon);

        Ride ride = new Ride();
        ride.setMaxPassengers(Integer.parseInt(maxPassengers));
        ride.setPrice(BigDecimal.valueOf(Haversine.calculateDistance(startLat, startLon, destLat, destLon) * 0.30));
        ride.setStartLocation(departureLocation);
        ride.setDestinationLocation(destinationLocation);
        ride.setStartLatCoordinates(startLat);
        ride.setStartLonCoordinates(startLon);
        ride.setDestLonCoordinates(destLon);
        ride.setDestLatCoordinates(destLat);
        ride.setRadius(123);
        ride.setPassengerCount(0);
        ride.setDriver(driver);
        ride.setStartTime(startTime);

        rideRepo.saveAndFlush(ride);

        return "redirect:/home";
    }


}
