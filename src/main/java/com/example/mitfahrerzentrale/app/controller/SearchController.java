package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    private final RideRepo rideRepo;

    public SearchController(RideRepo rideRepo) {
        this.rideRepo = rideRepo;
    }

    @GetMapping("/search-rides")
    public String searchRides(Model model, @RequestParam String startLocation, @RequestParam String endLocation, @RequestParam LocalDateTime startTime) {
        Optional<List<Ride>> rides = rideRepo.findRideByStartLocationAndDestinationLocationAndIsActiveTrueAndStartTime(startLocation, endLocation, startTime.atZone(ZoneId.of("Europe/Berlin")).toInstant());


    }



}
