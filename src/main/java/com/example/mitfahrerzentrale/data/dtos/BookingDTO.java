package com.example.mitfahrerzentrale.data.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BookingDTO {

    private Integer id;

    private RIdeDTO ride;

    private UserDTO passenger;

    private Integer passengerCount;

    private String bookingStatus;

    private Instant bookedAt;
}
