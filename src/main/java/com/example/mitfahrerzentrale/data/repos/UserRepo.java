package com.example.mitfahrerzentrale.data.repos;

import com.example.mitfahrerzentrale.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {


    User findUserByName(String name);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    boolean existsByPhoneNumber(String phone);
}
