package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.UserDTO;
import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UUID createUser(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO getUserById(UUID userId);

    List<UserDetailsDTO> getAllUsers();

    void updateUser(UserDetailsDTO userDetailsDTO);

    void deleteUser(UUID userId);

}
