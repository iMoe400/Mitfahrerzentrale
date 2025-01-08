package com.example.mitfahrerzentrale.data.repos;

import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepo extends JpaRepository<Ride, Integer> {

    Optional<List<Ride>> findByStartLocationContainingIgnoreCase(String startLocation);
    Optional<List<Ride>> findByDestinationLocationContainingIgnoreCase(String destinationLocation);
    Optional<List<Ride>> findByStartLocationContainingIgnoreCaseAndDestinationLocationContainingIgnoreCase(String startLocation, String destinationLocation);
    Optional<List<Ride>> findRideByDriverId(int driverId);
    Optional<Ride>findRideById(int id);
    Optional<List<Ride>> findRideByStartLocationAndDestinationLocationAndIsActiveTrueAndStartTime(String startLocation, String destinationLocation, Instant startTime);
    Optional<List<Ride>> findRideByDriverId(Integer driver_id);
    Optional<Ride> findRideByBookings(List<Booking> bookings);

    Optional<List<Ride>> findByStartLocationContainingIgnoreCaseAndDestinationLocationContainingIgnoreCaseAndDriverIdNot(
            String startLocation, String destinationLocation, Integer driverId);

    Optional<List<Ride>> findByStartLocationContainingIgnoreCaseAndDriverIdNot(
            String startLocation, Integer driverId);

    Optional<List<Ride>> findByDestinationLocationContainingIgnoreCaseAndDriverIdNot(
            String destinationLocation, Integer driverId);
}
