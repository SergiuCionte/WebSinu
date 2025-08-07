package com.PSapplication.PSapplication.Repositories;

import com.PSapplication.PSapplication.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
