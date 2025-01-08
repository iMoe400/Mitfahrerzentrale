package com.example.mitfahrerzentrale.app.services;

import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.repos.BookingRepo;
import com.example.mitfahrerzentrale.data.repos.RideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    @Autowired
    private final RideRepo rideRepository;
    @Autowired
    private final BookingRepo bookingRepository;

    public RideService(RideRepo rideRepository, BookingRepo bookingRepository) {
        this.rideRepository = rideRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Ride> getRidesOfferedByUser(Integer userId) {
        // Angebotene Fahrten eines Nutzers abrufen
        if(rideRepository.findRideByDriverId(userId).isPresent()) {
            return rideRepository.findRideByDriverId(userId).get();
        }
        return null;
    }

    public List<Booking> getRidesBookedByUser(Integer userId) {
        // Gebuchte Fahrten eines Nutzers abrufen
        if(bookingRepository.findAllByPassengerId(userId).isPresent()){
            return bookingRepository.findAllByPassengerId(userId).get();
        }
        return null;
    }

    public void deleteOfferedRide(Integer rideId) {
        // Überprüfen, ob der Nutzer die Fahrt tatsächlich anbietet
        Ride ride = rideRepository.findRideById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Fahrt-ID oder Berechtigung verweigert"));

        // Fahrt löschen
        rideRepository.delete(ride);
    }

    public void deleteBookedRide(Integer bookingId, Integer rideId) {
        Optional<Ride> ride = rideRepository.findRideById(rideId);

        // Überprüfen, ob der Nutzer die Fahrt gebucht hat
        Booking booking = bookingRepository.findBookingById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Buchung oder Berechtigung verweigert"));
        if(bookingRepository.findAllByRideId(rideId).isPresent()){
            ride = rideRepository.findRideByBookings(bookingRepository.findAllByRideId(rideId).get());
            ride.ifPresent(value -> value.getBookings().remove(booking));
        }

        // Buchung löschen
        bookingRepository.delete(booking); // Entfernt den Nutzer aus der Passagierliste
        ride.ifPresent(rideRepository::save);
    }
}
