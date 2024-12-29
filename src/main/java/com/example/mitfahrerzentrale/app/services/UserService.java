package com.example.mitfahrerzentrale.app.services;

import com.example.mitfahrerzentrale.data.dtos.UserDTO;
import com.example.mitfahrerzentrale.data.entities.User;

public class UserService {

    public static UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setRole(user.getRole());
        userDTO.setPasswordHash(user.getPasswordHash());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }


}
