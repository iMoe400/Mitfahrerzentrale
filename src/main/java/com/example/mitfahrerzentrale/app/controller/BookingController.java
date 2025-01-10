package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

@Controller
public class BookingController {

    private final BookingRepo bookingRepo;
    private final RideRepo rideRepo;
    private final UserRepo userRepo;

    public BookingController(BookingRepo bookingRepo, RideRepo rideRepo, UserRepo userRepo) {
        this.bookingRepo = bookingRepo;
        this.rideRepo = rideRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/create-booking")
    public String createBooking(Authentication authentication, @RequestParam Integer rideId, Model model, RedirectAttributes redirectAttributes) {
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        if (rideId != null) {
            Optional<Ride> rideOpt = rideRepo.findById(rideId);
            if (rideOpt.isPresent()) {
                Ride ride = rideOpt.get();
                if (!Objects.equals(ride.getPassengerCount(), ride.getMaxPassengers())) {
                    LocalDateTime startTime = ride.getStartTime().atZone(zoneId).toLocalDateTime();
                    Instant startInstant = startTime.atZone(zoneId).toInstant();
                    Instant startWindow = startInstant.minus(Duration.ofHours(1));
                    Instant endWindow = startInstant.plus(Duration.ofHours(1));
                    // Überprüfen, ob der Nutzer bereits eine Buchung in diesem Zeitfenster hat
                    if (bookingRepo.existsByPassengerAndRideStartTimeBetween(authentication.getName(), startTime.minusHours(1).atZone(zoneId).toInstant(), startTime.plusHours(1).atZone(zoneId).toInstant())) {
                        model.addAttribute("error", "Du hast bereits eine Fahrt in diesem Zeitraum gebucht.");
                        return "redirect:/home";
                    }
                    // Überprüfen, ob der Nutzer bereits eine Fahrt in dem Zeitfenster hat
                    if (rideRepo.existsByCreatorAndStartTimeBetween(authentication.getName(), startWindow, endWindow)) {
                        redirectAttributes.addFlashAttribute("error", "Du kannst keine Fahrt buchen, wenn du zu dieser Zeit eine eigene Fahrt anbietest.");
                        return "redirect:/home";
                    }

                    try {
                        Booking booking = new Booking();
                        Optional<Ride> optionalRIde = rideRepo.findRideById(rideId);

                        optionalRIde.ifPresent(booking::setRide);
                        booking.setPassenger(userRepo.findUserByName(authentication.getName()));
                        if (ride.getPassengerCount() != null) {
                            booking.setPassengerCount(ride.getMaxPassengers());
                            ride.setPassengerCount(ride.getPassengerCount() + 1);
                        } else {
                            booking.setPassengerCount(1);
                            ride.setPassengerCount(1);
                        }
                        booking.setIsActive(true);
                        bookingRepo.save(booking);


                        rideRepo.save(ride);

                    } catch (Exception e) {
                        redirectAttributes.addFlashAttribute("error", e.getMessage());
                    }

                } else {
                    redirectAttributes.addFlashAttribute("error", "Ride already full");
                }
            } else {
                model.addAttribute("booking", new Booking());
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "error");
        }

        return "redirect:/home";
    }

}
