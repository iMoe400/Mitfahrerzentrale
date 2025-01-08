package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import com.example.mitfahrerzentrale.geo.GeocodeController;
import com.example.mitfahrerzentrale.geo.calculation.Haversine;
import com.example.mitfahrerzentrale.geo.dto.NominatimResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RideController {

    @Autowired
    GeocodeController geocodeController;
    @Autowired
    private RideRepo rideRepo;
    @Autowired
    private UserRepo userRepo;


    @PostMapping("/create-ride")
    public String createRide(Authentication authentication, @RequestParam("maxPassengers") Integer maxPassengers,
                             @RequestParam("departureLocation") String departureLocation,
                             @RequestParam("destinationLocation") String destinationLocation,
                             @RequestParam("departureTime") LocalDateTime departureTime,
                             Model model){
        System.out.println(maxPassengers);
        double destLat = Double.parseDouble(geocodeController.searchSingleLocation(destinationLocation).getLat());
        double destLon = Double.parseDouble(geocodeController.searchSingleLocation(destinationLocation).getLon());
        double startLat = Double.parseDouble(geocodeController.searchSingleLocation(departureLocation).getLat());
        double startLon =  Double.parseDouble(geocodeController.searchSingleLocation(departureLocation).getLon());
        Ride ride = new Ride();
        ride.setMaxPassengers(maxPassengers);
        ride.setPrice(BigDecimal.valueOf(Haversine.calculateDistance(startLat, startLon, destLat, destLon)*0.30));
        ride.setStartLocation(departureLocation);
        ride.setDestinationLocation(destinationLocation);
        ride.setStartLatCoordinates(startLat);
        ride.setStartLonCoordinates(startLon);
        ride.setDestLonCoordinates(destLon);
        ride.setDestLatCoordinates(destLat);
        ride.setRadius(123);
        ride.setPassengerCount(0);
        ride.setDriver(userRepo.findUserByName(authentication.getName()));
        ride.setStartTime(departureTime.atZone(ZoneId.of("Europe/Berlin")).toInstant());
        rideRepo.saveAndFlush(ride);

        return "redirect:/home";
    }


}
