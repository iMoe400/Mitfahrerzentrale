package com.example.mitfahrerzentrale.data.dtos;


import java.time.Instant;

public class BookingDTO {

    private Integer id;

    private RIdeDTO ride;

    private UserDTO passenger;

    private Integer passengerCount;

    private String bookingStatus;

    private Instant bookedAt;
}
