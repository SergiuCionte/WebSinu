package com.PSapplication.PSapplication.DTOs;

import com.PSapplication.PSapplication.Entities.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private UUID id;

    private String role;

    private String username;

    private String email;
}
