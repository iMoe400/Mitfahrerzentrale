package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String createBooking(Authentication authentication, @RequestParam Integer rideId, Model model) {

        if(rideId != null) {
            if(rideRepo.findRideById(rideId).isPresent()) {
                Ride ride = rideRepo.findRideById(rideId).get();

                try {
                    Booking booking = new Booking();
                    booking.setRide(rideRepo.findRideById(rideId).get());
                    booking.setPassenger(userRepo.findUserByName(authentication.getName()));
                    booking.setPassengerCount(ride.getPassengerCount());
                    booking.setBookingStatus("AKTIV");
                    booking.setIsActive(true);
                    bookingRepo.save(booking);

                    ride.setPassengerCount(ride.getPassengerCount() + 1);
                    rideRepo.save(ride);

                } catch (Exception e) {
                    model.addAttribute("error", e.getMessage());
                }
            } else {
                model.addAttribute("booking", new Booking());
            }
        } else {
            model.addAttribute("error", "error");
        }

        return "redirect:/home";
    }
}
