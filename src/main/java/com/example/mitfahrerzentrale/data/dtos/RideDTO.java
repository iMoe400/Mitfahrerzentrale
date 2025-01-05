package com.example.mitfahrerzentrale.data.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class RideDTO {

    private Integer id;
    private Integer driverId;
    private Integer maxPassengers;
    private Integer passengerCount;
    private BigDecimal price;
    private String status;
    private String startLocation;
    private String destinationLocation;
    private Integer radius;
    private Boolean isActive;
    private Instant createdAt;
    private Instant arrivalTime;
    private Instant startTime;
    private Double destLatCoordinates;
    private Double startLatCoordinates;
    private Double startLonCoordinates;
    private Double destLonCoordinates;
}
