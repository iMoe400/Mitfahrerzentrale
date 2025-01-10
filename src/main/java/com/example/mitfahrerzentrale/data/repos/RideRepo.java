package com.example.mitfahrerzentrale.data.repos;

import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RideRepo extends JpaRepository<Ride, Integer> {


    Optional<Ride> findRideById(int id);


    Optional<List<Ride>> findRideByDriverId(Integer driver_id);

    @Query("""
                SELECT r
                FROM Ride r
                JOIN r.bookings b
                WHERE b.id IN :bookingIds
            """)
    Optional<Ride> findRideByBookings(@Param("bookingIds") Set<Integer> bookingIds);

    Optional<List<Ride>> findByStartLocationContainingIgnoreCaseAndDestinationLocationContainingIgnoreCaseAndDriverIdNot(String startLocation, String destinationLocation, Integer driverId);

    @Query("""
                SELECT COUNT(r) > 0
                FROM Ride r
                WHERE r.driver = :driver
                  AND r.startTime BETWEEN :startWindow AND :endWindow
            """)
    boolean existsRideWithinTwoHours(User driver, Instant startWindow, Instant endWindow);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " + "FROM Ride r WHERE r.driver.name = :creator " + "AND r.startTime BETWEEN :start AND :end")
    boolean existsByCreatorAndStartTimeBetween(@Param("creator") String creator, @Param("start") Instant start, @Param("end") Instant end);

}
