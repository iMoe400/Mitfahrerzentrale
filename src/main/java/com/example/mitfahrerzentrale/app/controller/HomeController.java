package com.example.mitfahrerzentrale.app.controller;

import com.example.mitfahrerzentrale.app.services.DtoWrapper;
import com.example.mitfahrerzentrale.data.dtos.BookingDTO;
import com.example.mitfahrerzentrale.data.dtos.RideDTO;
import com.example.mitfahrerzentrale.data.dtos.UserDTO;
import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import com.example.mitfahrerzentrale.geo.dto.NominatimResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String home(Authentication authentication,
                       RedirectAttributes redirectAttributes,
                       Model model,
                       @ModelAttribute List<NominatimResponseDTO> seachResponseList) {
        User currentUser = userRepo.findUserByName(authentication.getName());
        Optional<List<Ride>> rides = rideRepo.findRideByDriverId(currentUser.getId());
        Optional<List<Booking>> bookings = bookingRepo.findBookingsByPassengerId(currentUser.getId());
        if(bookings.isPresent()){
            List<BookingDTO> bookingDTOList = DtoWrapper.bookingsToDTOs(bookings.get());
            model.addAttribute("bookings", bookingDTOList);
        }
        if(rides.isPresent()) {
            List<RideDTO> rideDTOList = DtoWrapper.ridesToDTOs(rides.get());
            model.addAttribute("rides", rideDTOList);
        }

        UserDTO userDTO = DtoWrapper.userToDTO(userRepo.findUserByName(authentication.getName()));
        model.addAttribute("user", userDTO);
        return "home";
    }



}
