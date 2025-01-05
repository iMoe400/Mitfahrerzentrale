package com.example.mitfahrerzentrale.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bookings", schema = "mitfahrerzentrale")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "RideId", nullable = false)
    private Ride ride;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PassengerId", nullable = false)
    private User passenger;

    @Column(name = "PassengerCount", nullable = false)
    private Integer passengerCount;

    @Lob
    @Column(name = "BookingStatus")
    private String bookingStatus;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = true;

    @Column(name = "BookedAt", nullable = false)
    @CreationTimestamp
    private Instant bookedAt;

}