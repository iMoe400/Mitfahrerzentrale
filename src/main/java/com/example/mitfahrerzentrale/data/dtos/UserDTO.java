package com.example.mitfahrerzentrale.data.dtos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserDTO {

    private Integer id;

    private String name;

    private String email;

    private String phoneNumber;

    private String passwordHash;

    private String role;

    private Boolean isActive = false;

    private Instant createdAt;
}
