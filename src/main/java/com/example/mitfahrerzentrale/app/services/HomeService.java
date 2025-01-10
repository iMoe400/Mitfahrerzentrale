package com.example.mitfahrerzentrale.app.services;

import com.example.mitfahrerzentrale.data.dtos.BookingRideDTO;
import com.example.mitfahrerzentrale.data.dtos.RideDTO;
import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final RideRepo rideRepo;
    @Autowired
    private final BookingRepo bookingRepo;
    @Autowired
    private final RideService rideService;

    public HomeService(UserRepo userRepo, RideRepo rideRepo, BookingRepo bookingRepo, RideService rideService) {
        this.userRepo = userRepo;
        this.rideRepo = rideRepo;
        this.bookingRepo = bookingRepo;
        this.rideService = rideService;
    }

    public void prepareAdminModel(User currentUser, Model model) {
        List<Ride> rides = rideRepo.findAll();
        List<Booking> bookings = bookingRepo.findAll();

        model.addAttribute("user", DtoWrapper.userToDTO(userRepo.findUserByName(currentUser.getName())));
        model.addAttribute("rides", DtoWrapper.ridesToDTOs(rides));

        List<BookingRideDTO> bookingRideDTOList = bookings.stream()
                .map(booking -> new BookingRideDTO(
                        booking.getId(),
                        booking.getRide().getStartLocation(),
                        booking.getRide().getDestinationLocation(),
                        DateTimeFormatterUtil.formatWithZone(booking.getRide().getStartTime().atZone
                                (ZoneId.of("Europe/Berlin")).toLocalDateTime(), "Europe/Berlin", "dd.MM.yyyy HH:mm"),
                        booking.getPassengerCount(),
                        booking.getBookingStatus(),
                        booking.getRide().getId(),
                        booking.getRide().getMaxPassengers()
                ))
                .collect(Collectors.toList());

        model.addAttribute("bookings", bookingRideDTOList);
    }

    public void prepareUserModel(User currentUser, Model model, String locationData, String locationLat,
                                 String locationLon, String searchType, String departureData, String departureLat,
                                 String departureLon, String destinationData, String destinationLat,
                                 String destinationLon) {
        model.addAttribute("user", DtoWrapper.userToDTO(currentUser));

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

        List<Ride> offeredRides = rideService.getRidesOfferedByUser(currentUser.getId());

        List<RideDTO> offeredRideDTOs = offeredRides.stream()
                .map(ride -> {
                    RideDTO rideDTO = new RideDTO();
                    rideDTO.setId(ride.getId());
                    rideDTO.setStartLocation(ride.getStartLocation());
                    rideDTO.setDestinationLocation(ride.getDestinationLocation());

                    String formattedStartTime = DateTimeFormatterUtil.formatFromInstant(
                            ride.getStartTime(), "Europe/Berlin", "dd.MM.yyyy HH:mm");
                    rideDTO.setStartTime(formattedStartTime);

                    return rideDTO;
                })
                .collect(Collectors.toList());

        model.addAttribute("offeredRides", offeredRideDTOs);

        Optional<List<Booking>> bookings = bookingRepo.findBookingsByPassengerId(currentUser.getId());
        if (bookings.isPresent()) {
            List<BookingRideDTO> bookingRideDTOList = bookings.get().stream()
                    .map(booking -> new BookingRideDTO(
                            booking.getId(),
                            booking.getRide().getStartLocation(),
                            booking.getRide().getDestinationLocation(),
                            DateTimeFormatterUtil.formatWithZone(booking.getRide().getStartTime().atZone
                                    (ZoneId.of("Europe/Berlin")).toLocalDateTime(), "Europe/Berlin", "dd.MM.yyyy HH:mm"),
                            booking.getRide().getPassengerCount(),
                            booking.getBookingStatus(),
                            booking.getRide().getId(),
                            booking.getRide().getMaxPassengers()
                    ))
                    .collect(Collectors.toList());
            model.addAttribute("bookings", bookingRideDTOList);
        } else {
            model.addAttribute("bookings", List.of());
        }
    }
}
