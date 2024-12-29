package com.example.mitfahrerzentrale.data.repos;

import com.example.mitfahrerzentrale.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findUserById(int id);
    User findUserByName(String name);
}
