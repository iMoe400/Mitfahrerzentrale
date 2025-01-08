package com.example.mitfahrerzentrale.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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

    @Column(name = "StartLocation", nullable = false)
    private String startLocation;

    @Column(name = "DestinationLocation", nullable = false)
    private String destinationLocation;

    @Column(name = "Radius")
    private Integer radius;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = false;

    @Column(name = "CreatedAt", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "ArrivalTime")
    private Instant arrivalTime;

    @Column(name = "DestLatCoordinates")
    private Double destLatCoordinates;

    @Column(name = "StartLatCoordinates")
    private Double startLatCoordinates;

    @Column(name = "StartLonCoordinates")
    private Double startLonCoordinates;

    @Column(name = "DestLonCoordinates")
    private Double destLonCoordinates;

    @Column(name = "StartTime")
    private Instant startTime;

    @Column(name = "PassengerCount")
    private Integer passengerCount;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Booking> bookings = new LinkedHashSet<>();

}