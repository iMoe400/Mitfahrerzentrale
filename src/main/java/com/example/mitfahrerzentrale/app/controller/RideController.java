package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Ride;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Controller
public class RideController {

    @PostMapping("/show-create-ride")
    public String showCreateRide(@RequestParam String departureLocation,
                                 @RequestParam String destinationLocation,
                                 @RequestParam String departureDate,
                                 @RequestParam Integer radius,
                                 Model model) {
        model.addAttribute("departureDate", Objects.requireNonNullElse(departureDate, ""));
        model.addAttribute("destinationLocation", Objects.requireNonNullElse(destinationLocation, ""));
        model.addAttribute("departureLocation", Objects.requireNonNullElse(departureLocation, ""));
        model.addAttribute("radius", Objects.requireNonNullElse(radius, ""));

        return "create-ride";
    }

    @PostMapping("/create-ride")
    public String createRide( @RequestParam("maxPassengers") Integer maxPassengers,
                              @RequestParam("price") BigDecimal price,
                              @RequestParam("departureLocation") String departureLocation,
                              @RequestParam("destinationLocation") String destinationLocation,
                              @RequestParam("departureTime") LocalDateTime departureTime,
                              @RequestParam("radius") Integer radius,
                              Model model){

        Ride ride = new Ride();
        ride.setMaxPassengers(maxPassengers);
        ride.setPrice(price);
        ride.setDepartureLocation(departureLocation);
        ride.setDestinationLocation(destinationLocation);
        ride.setRadius(radius);
        ride.setDepartureTime(departureTime.atZone(ZoneId.of("Europe/Berlin")).toInstant());

        return "redirect:/show-create-ride";
    }


}
