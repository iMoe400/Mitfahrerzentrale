package com.example.mitfahrerzentrale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.mitfahrerzentrale.data.repos")
public class MitfahrerzentraleApplication {

    public static void main(String[] args) {

        SpringApplication.run(MitfahrerzentraleApplication.class, args);

    }

}
