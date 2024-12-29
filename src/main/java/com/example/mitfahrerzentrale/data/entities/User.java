package com.example.mitfahrerzentrale.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "users", schema = "mitfahrerzentrale")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Column(name = "Email", nullable = false, length = 150)
    private String email;

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;

    @Lob
    @Column(name = "Role")
    private String role;

    @Column(name = "IsActive", nullable = false)
    @ColumnDefault("1")
    private Boolean isActive;

    @Column(name = "CreatedAt", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @OneToMany(mappedBy = "passenger")
    private Set<Booking> bookings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "driver")
    private Set<Ride> rides = new LinkedHashSet<>();


}