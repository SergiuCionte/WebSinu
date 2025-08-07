package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.Builders.MaterieBuilder;
import com.PSapplication.PSapplication.DTOs.Builders.UserBuilder;
import com.PSapplication.PSapplication.DTOs.MaterieDetailsDTO;
import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.Materie;
import com.PSapplication.PSapplication.Entities.User;
import com.PSapplication.PSapplication.Repositories.MaterieRepository;
import com.PSapplication.PSapplication.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


/**
 * Service class for handling operations related to Materie entities.
 */
@Service
@AllArgsConstructor
public class MaterieServiceImpl implements MaterieService {

    private static final Logger LOGGER=LoggerFactory.getLogger(MaterieService.class);
    private final MaterieRepository materieRepository;
    private final UserRepository userRepository;


    /**
     * Creates a new Materie entity based on the provided MaterieDetailsDTO.
     *
     * @param materieDetailsDTO The DTO containing details of the Materie to be created.
     * @return The ID of the newly created Materie.
     */
    @Override
    public Long createMaterie(MaterieDetailsDTO materieDetailsDTO) {
        Materie materie = MaterieBuilder.toEntity(materieDetailsDTO);
        materie=materieRepository.save(materie);
        LOGGER.info("Materie with id {} was inserted in db",materie.getMaterie_id());
        return materie.getMaterie_id();
    }

    /**
     * Retrieves a Materie entity by its ID.
     *
     * @param id The ID of the Materie to retrieve.
     * @return The MaterieDetailsDTO corresponding to the specified ID.
     * @throws RuntimeException if the Materie with the given ID does not exist.
     */
    @Override
    public MaterieDetailsDTO getMaterieById(Long id) {
        Optional<Materie> materieOptional = materieRepository.findById(id);
        if (!materieOptional.isPresent()) {
            LOGGER.error("Materie with id {} was not found in db", id);
            throw new RuntimeException(Materie.class.getSimpleName() + " with id: " + id);
        }
        return MaterieBuilder.toMaterieDetailsDTO(materieOptional.get());
    }

    /**
     * Retrieves a list of all Materie entities.
     *
     * @return A list of MaterieDTO objects representing all Materie entities.
     */
    @Override
    public List<MaterieDetailsDTO> getAllMateries() {
        List<Materie> materieList=materieRepository.findAll();
        //return userList.stream().map(UserBuilder::toUserDto).collect(Collectors.toList());
        return materieList.stream().map(MaterieBuilder::toMaterieDetailsDTO).collect(Collectors.toList());
    }

    public List<UserDetailsDTO> getAllUsersFromMaterie() {
        List<User> userList=userRepository.findAll();
        //return userList.stream().map(UserBuilder::toUserDto).collect(Collectors.toList());
        return userList.stream().map(UserBuilder::toUserDetailsDTO).collect(Collectors.toList());
    }

    /**
     * Updates an existing Materie entity based on the provided MaterieDetailsDTO.
     *
     * @param materieDetailsDTO The DTO containing updated details of the Materie.
     * @throws RuntimeException if the Materie with the given ID does not exist.
     */
    @Override
    public void updateMaterie(MaterieDetailsDTO materieDetailsDTO) {
        Optional<Materie> optionalMaterie = materieRepository.findById(materieDetailsDTO.getMaterie_id());
        if (!optionalMaterie.isPresent()) {
            LOGGER.error("Materie with id {} was not found in db", materieDetailsDTO.getMaterie_id());
            throw new RuntimeException(Materie.class.getSimpleName() + " with id: " + materieDetailsDTO.getMaterie_id() + " not found");
        }

        Materie materie = optionalMaterie.get(); // Retrieve the user from Optional

        // Update the user fields with values from userDetailsDTO
        materie.setNume_materie(materieDetailsDTO.getNume_materie());
       // materie.setLaborator(materieDetailsDTO.getLaborator());
        materie.setExamen(materieDetailsDTO.getExamen());


        // Save the updated user
        materieRepository.save(materie);

        LOGGER.info("Materie with id {} was updated in db", materie.getMaterie_id());
    }
    /**
     * Deletes a Materie entity by its ID.
     *
     * @param materieId The ID of the Materie to delete.
     * @throws RuntimeException if the Materie with the given ID does not exist.
     */
    @Override
    public void deleteMaterie(Long materieId) {
        Optional<Materie> optionalMaterie = materieRepository.findById(materieId);
        if (!optionalMaterie.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", materieId);
            throw new RuntimeException(User.class.getSimpleName() + " with id: " + materieId);
        }

        materieRepository.delete(optionalMaterie.get());
        LOGGER.info("Materie with id {} was deleted from db", materieId);
    }
}
