package com.example.mitfahrerzentrale.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "rides", schema = "mitfahrerzentrale")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RideId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DriverId", nullable = false)
    private User driver;

    @Column(name = "MaxPassengers", nullable = false)
    private Integer maxPassengers;

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Lob
    @Column(name = "Status")
    private String status;

    @Column(name = "DepartureLocation", nullable = false)
    private String departureLocation;

    @Column(name = "DestinationLocation", nullable = false)
    private String destinationLocation;

    @Column(name = "Coordinates", length = 100)
    private String coordinates;

    @Column(name = "DepartureTime", nullable = false)
    private Instant departureTime;

    @Column(name = "Radius")
    private Integer radius;

    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;

}