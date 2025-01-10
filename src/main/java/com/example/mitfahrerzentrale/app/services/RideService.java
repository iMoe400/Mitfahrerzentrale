package com.example.mitfahrerzentrale.app.services;

import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RideService {


    private final RideRepo rideRepository;

    private final BookingRepo bookingRepository;

    RideService(@Autowired RideRepo rideRepo, @Autowired BookingRepo bookingRepo) {
        this.rideRepository = rideRepo;
        this.bookingRepository = bookingRepo;
    }


    public List<Ride> getRidesOfferedByUser(Integer userId) {
        // Angebotene Fahrten eines Nutzers abrufen
        if(rideRepository.findRideByDriverId(userId).isPresent()) {
            return rideRepository.findRideByDriverId(userId).get();
        }
        return null;
    }

    public void deleteOfferedRide(Integer rideId, int id) {

        Ride ride = rideRepository.findRideById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Fahrt-ID oder Berechtigung verweigert"));

        // Fahrt löschen
        rideRepository.delete(ride);
    }

    public void deleteBookedRide(Integer bookingId, Integer rideId) {
        Optional<Ride> ride = rideRepository.findRideById(rideId);


        Booking booking = bookingRepository.findBookingById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Buchung oder Berechtigung verweigert"));

        Optional<List<Booking>> bookingList = bookingRepository.findAllByRideId(rideId);
        if(bookingList.isPresent()){
            Set<Booking> bookingSet = new HashSet<>(bookingList.get());
            Set<Integer> bookingIds = bookingSet.stream()
                    .map(Booking::getId) // IDs extrahieren
                    .collect(Collectors.toSet());
            ride = rideRepository.findRideByBookings(bookingIds);
            ride.ifPresent(value -> value.getBookings().remove(booking));
            if(ride.isPresent()) {
                ride.get().setPassengerCount(ride.get().getPassengerCount()-1);
                rideRepository.save(ride.get());
            }

        }

        // Buchung löschen
        bookingRepository.delete(booking); // Entfernt den Nutzer aus der Passagierliste
        ride.ifPresent(rideRepository::save);
    }
}
