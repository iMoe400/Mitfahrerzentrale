package com.example.mitfahrerzentrale.data.repos;

import com.example.mitfahrerzentrale.data.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {

    Optional<List<Booking>> findBookingsByPassengerId(int passenger);

    Optional<Booking> findBookingById(int id);

    Optional<List<Booking>> findAllByRideId(Integer id);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " + "FROM Booking b WHERE b.passenger.name = :passenger " + "AND b.ride.startTime BETWEEN :start AND :end")
    boolean existsByPassengerAndRideStartTimeBetween(@Param("passenger") String passenger, @Param("start") Instant start, @Param("end") Instant end);
}
