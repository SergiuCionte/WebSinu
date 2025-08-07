package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.Builders.UserBuilder;
import com.PSapplication.PSapplication.DTOs.UserDTO;
import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.User;
import com.PSapplication.PSapplication.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

/**
 * Service class for handling operations related to User entities.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER=LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;


    /**
     * Creates a new User entity based on the provided UserDetailsDTO.
     *
     * @param userDetailsDTO The DTO containing details of the User to be created.
     * @return The ID of the newly created User.
     */
    @Override
    public UUID createUser(UserDetailsDTO userDetailsDTO) {
        User user=UserBuilder.toEntity(userDetailsDTO);

        user=userRepository.save(user);
        LOGGER.info("User with id {} was inserted in db",user.getId());
        return user.getId();
    }

    /**
     * Retrieves a User entity by its ID.
     *
     * @param id The ID of the User to retrieve.
     * @return The UserDetailsDTO corresponding to the specified ID.
     * @throws RuntimeException if the User with the given ID does not exist.
     */
    @Override
    public UserDetailsDTO getUserById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new RuntimeException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDetailsDTO(userOptional.get());
    }

    /**
     * Retrieves a list of all User entities.
     *
     * @return A list of UserDTO objects representing all User entities.
     */
    @Override
    public List<UserDetailsDTO> getAllUsers() {
        List<User> userList=userRepository.findAll();
        //return userList.stream().map(UserBuilder::toUserDto).collect(Collectors.toList());
        return userList.stream().map(UserBuilder::toUserDetailsDTO).collect(Collectors.toList());
    }

    /**
     * Updates an existing User entity based on the provided UserDetailsDTO.
     *
     * @param userDetailsDTO The DTO containing updated details of the User.
     * @throws RuntimeException if the User with the given ID does not exist.
     */
    @Override
    public void updateUser(UserDetailsDTO userDetailsDTO) {
        Optional<User> optionalUser = userRepository.findById(userDetailsDTO.getId());
        if (!optionalUser.isPresent()) {
            LOGGER.error("User with id {} was not found in db", userDetailsDTO.getId());
            throw new RuntimeException(User.class.getSimpleName() + " with id: " + userDetailsDTO.getId() + " not found");
        }

        User user = optionalUser.get(); // Retrieve the user from Optional

        // Update the user fields with values from userDetailsDTO
        user.setUsername(userDetailsDTO.getUsername());
        user.setEmail(userDetailsDTO.getEmail());
        user.setParola(userDetailsDTO.getParola());
        user.setMaterieList(userDetailsDTO.getMaterieList());
        user.setRole(userDetailsDTO.getRole());
        // Assuming other fields need to be updated similarly

        // Save the updated user
        userRepository.save(user);

        LOGGER.info("User with id {} was updated in db", user.getId());
    }
    /**
     * Deletes a User entity by its ID.
     *
     * @param userId The ID of the User to delete.
     * @throws RuntimeException if the User with the given ID does not exist.
     */
    @Override
    public void deleteUser(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", userId);
            throw new RuntimeException(User.class.getSimpleName() + " with id: " + userId);
        }

        userRepository.delete(optionalUser.get());
        LOGGER.info("Person with id {} was deleted from db", userId);
    }
}
