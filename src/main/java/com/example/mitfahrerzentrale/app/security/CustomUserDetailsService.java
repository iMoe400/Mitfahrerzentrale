package com.example.mitfahrerzentrale.app.security;


import com.example.mitfahrerzentrale.data.entities.User;
import com.example.mitfahrerzentrale.data.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else if (user.getRole() == null) {
            throw new IllegalStateException("User has no role assigned");
        }

        return new CustomUserDetails(user);
    }
}
