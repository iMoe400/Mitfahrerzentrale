package com.example.mitfahrerzentrale.data.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BookingDTO {
    private Integer id;
    private RideDTO ride; // Referenz auf die Ride-ID
    private Integer passengerId; // Referenz auf die Passenger-ID
    private Integer passengerCount;
    private String bookingStatus;
    private Boolean isActive;
    private Instant bookedAt;
}
