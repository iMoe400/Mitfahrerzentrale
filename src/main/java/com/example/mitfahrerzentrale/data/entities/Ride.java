package com.example.mitfahrerzentrale.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "rides", schema = "mitfahrerzentrale")
public class Ride {
    @Id
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

    @Column(name = "DestinationCoordinates")
    private BigDecimal destinationCoordinates;

    @Column(name = "DepartureCoordinates")
    private BigDecimal departureCoordinates;

    @Column(name = "DepartureTime")
    private Instant departureTime;

    @Column(name = "Radius")
    private Integer radius;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = false;

    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;

    @Column(name = "ArrivalTime")
    private Instant arrivalTime;


    @OneToMany(mappedBy = "ride")
    private Set<Booking> bookings = new LinkedHashSet<>();

}