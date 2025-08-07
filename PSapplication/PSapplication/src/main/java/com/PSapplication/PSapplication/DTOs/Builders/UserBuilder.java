package com.PSapplication.PSapplication.DTOs.Builders;

import com.PSapplication.PSapplication.DTOs.UserDTO;
import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.User;
import lombok.Builder;

@Builder
public class UserBuilder {
    private UserBuilder(){

    }

    public static UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())

                .role(user.getRole())

                .build();
    }
    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return UserDetailsDTO.builder()
                .id(user.getId())
                .role(user.getRole())
                .username(user.getUsername())
                .parola(user.getEmail())
                .email(user.getEmail())
                .materieList(user.getMaterieList())
              //  .notaColocviu(user.getNotaColocviu())
                .notaExamenList(user.getNotaExamenList())
                .build();
    }

    public static User toEntity(UserDetailsDTO userDetailsDTO){
        return User.builder()
                .id(userDetailsDTO.getId())
                .role(userDetailsDTO.getRole())
                .username(userDetailsDTO.getUsername())
                .parola(userDetailsDTO.getParola())
                .email(userDetailsDTO.getEmail())
                .materieList(userDetailsDTO.getMaterieList())
               // .notaColocviu(userDetailsDTO.getNotaColocviu())
                .notaExamenList(userDetailsDTO.getNotaExamenList())
                .build();
    }
}
