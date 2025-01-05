package com.example.mitfahrerzentrale.data.repos;

import com.example.mitfahrerzentrale.data.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepo extends JpaRepository<Ride, Integer> {


    Optional<List<Ride>> findRideByDriverId(int driverId);
    Optional<Ride>findRideById(int id);
}
