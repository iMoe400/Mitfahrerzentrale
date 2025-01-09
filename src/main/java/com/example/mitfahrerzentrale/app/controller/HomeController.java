package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.app.services.DtoWrapper;
import com.example.mitfahrerzentrale.app.services.RideService;
import com.example.mitfahrerzentrale.data.dtos.BookingRideDTO;
import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final RideRepo rideRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private RideService rideService;

    public HomeController(UserRepo userRepo, RideRepo rideRepo) {
        this.userRepo = userRepo;
        this.rideRepo = rideRepo;
    }
//Startseite Inhalt:
    //th:if="${fahrten}" -> div für die fahrten über suchleiste und ergebnissen
    //ansonsnten einfach nur suchleiste und ergebnisse, ggf vorläufige ergebnisse wenn Standort aktiviert
    //User nach Standort bei Anmeldung fragen?
    //User einem Wohnort zuordnen?


    @GetMapping("/home")
    public String home(
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model,
            @RequestParam(required = false) String locationData,
            @RequestParam(required = false) String locationLat,
            @RequestParam(required = false) String locationLon,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String departureData,
            @RequestParam(required = false) String departureLat,
            @RequestParam(required = false) String departureLon,
            @RequestParam(required = false) String destinationData,
            @RequestParam(required = false) String destinationLat,
            @RequestParam(required = false) String destinationLon) {

        if ("departure".equals(searchType)) {
            model.addAttribute("departureData", locationData);
            model.addAttribute("departureLat", locationLat);
            model.addAttribute("departureLon", locationLon);
            model.addAttribute("destinationData", destinationData);
            model.addAttribute("destinationLat", destinationLat);
            model.addAttribute("destinationLon", destinationLon);
        } else if ("destination".equals(searchType)) {
            model.addAttribute("destinationData", locationData);
            model.addAttribute("destinationLat", locationLat);
            model.addAttribute("destinationLon", locationLon);
            model.addAttribute("departureData", departureData);
            model.addAttribute("departureLat", departureLat);
            model.addAttribute("departureLon", departureLon);
        } else {
            model.addAttribute("departureData", departureData);
            model.addAttribute("departureLat", departureLat);
            model.addAttribute("departureLon", departureLon);
            model.addAttribute("destinationData", destinationData);
            model.addAttribute("destinationLat", destinationLat);
            model.addAttribute("destinationLon", destinationLon);
        }


        User currentUser = userRepo.findUserByName(authentication.getName());
        model.addAttribute("user", DtoWrapper.userToDTO(currentUser));

        List<Ride> offeredRides = rideService.getRidesOfferedByUser(currentUser.getId());
        model.addAttribute("offeredRides", offeredRides);

        Optional<List<Booking>> bookings = bookingRepo.findBookingsByPassengerId(currentUser.getId());
        if (bookings.isPresent()) {
            List<BookingRideDTO> bookingRideDTOList = bookings.get().stream()
                    .map(booking -> new BookingRideDTO(
                            booking.getId(),
                            booking.getRide().getStartLocation(),
                            booking.getRide().getDestinationLocation(),
                            booking.getRide().getStartTime().toString(),
                            booking.getPassengerCount(),
                            booking.getBookingStatus(),
                            booking.getRide().getId()
                    ))
                    .toList();
            model.addAttribute("bookings", bookingRideDTOList);
        } else {
            model.addAttribute("bookings", List.of());
        }

        return "home";
    }






}
