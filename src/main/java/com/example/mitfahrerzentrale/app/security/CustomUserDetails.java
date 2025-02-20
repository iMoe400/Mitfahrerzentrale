package com.example.mitfahrerzentrale.app.security;

import com.example.mitfahrerzentrale.data.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map roles to authorities, for example:
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }

    public int getId() {
        return user.getId();
    }


}
