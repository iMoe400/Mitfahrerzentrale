package com.example.mitfahrerzentrale.data.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRideDTO {
    private Integer bookingId;
    private String departureLocation;
    private String destinationLocation;
    private String departureTime;
    private Integer passengerCount;
    private String bookingStatus;
    private Integer rideId; // Hinzugefügt

    // Konstruktor anpassen
    public BookingRideDTO(Integer bookingId, String departureLocation, String destinationLocation,
                          String departureTime, Integer passengerCount, String bookingStatus, Integer rideId) {
        this.bookingId = bookingId;
        this.departureLocation = departureLocation;
        this.destinationLocation = destinationLocation;
        this.departureTime = departureTime;
        this.passengerCount = passengerCount;
        this.bookingStatus = bookingStatus;
        this.rideId = rideId;
    }
}
