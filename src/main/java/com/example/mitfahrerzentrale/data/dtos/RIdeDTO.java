package com.example.mitfahrerzentrale.data.dtos;

import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class RIdeDTO {

    private Integer id;

    private UserDTO driver;

    private Integer maxPassengers;

    private BigDecimal price;

    private String status;

    private String departureLocation;

    private String destinationLocation;

    private String coordinates;

    private Instant departureTime;

    private Integer radius;

    private Instant createdAt;
}
