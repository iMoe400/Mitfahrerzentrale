package com.example.mitfahrerzentrale.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Instant;

@Getter
@Setter
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

    @ColumnDefault("1")
    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = false;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false)
    private Instant createdAt;

}