package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.*;
import com.PSapplication.PSapplication.DTOs.Builders.ExamenBuilder;
import com.PSapplication.PSapplication.DTOs.Builders.MaterieBuilder;
import com.PSapplication.PSapplication.DTOs.Builders.UserBuilder;
import com.PSapplication.PSapplication.Entities.Examen;
import com.PSapplication.PSapplication.Entities.Materie;
import com.PSapplication.PSapplication.Entities.User;
import com.PSapplication.PSapplication.Repositories.ExamenRepository;
import com.PSapplication.PSapplication.Repositories.MaterieRepository;
import com.PSapplication.PSapplication.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


/**
 * Service class for handling operations related to Examen entities.
 */
@Service
@AllArgsConstructor
public class ExamenServiceImpl implements ExamenService {

    private static final Logger LOGGER=LoggerFactory.getLogger(ExamenService.class);
    private final ExamenRepository examenRepository;


    /**
     * Creates a new Examen entity based on the provided ExamenDetailsDTO.
     *
     * @param examenDetailsDTO The DTO containing details of the Examen to be created.
     * @return The ID of the newly created Examen.
     */
    @Override
    public Long createExamen(ExamenDetailsDTO examenDetailsDTO) {
        Examen examen = ExamenBuilder.toEntity(examenDetailsDTO);
        examen=examenRepository.save(examen);
        LOGGER.info("Examen with id {} was inserted in db",examen.getExamen_id());
        return examen.getExamen_id();
    }

    /**
     * Retrieves an Examen entity by its ID.
     *
     * @param id The ID of the Examen to retrieve.
     * @return The ExamenDetailsDTO corresponding to the specified ID.
     * @throws RuntimeException if the Examen with the given ID does not exist.
     */
    @Override
    public ExamenDetailsDTO getExamenById(Long id) {
        Optional<Examen> examenOptional = examenRepository.findById(id);
        if (!examenOptional.isPresent()) {
            LOGGER.error("Examen with id {} was not found in db", id);
            throw new RuntimeException(Examen.class.getSimpleName() + " with id: " + id);
        }
        return ExamenBuilder.toExamenDetailsDTO(examenOptional.get());

    }

    /**
     * Retrieves a list of all Examen entities.
     *
     * @return A list of ExamenDTO objects representing all Examen entities.
     */
    @Override
    public List<ExamenDetailsDTO> getAllExams() {
        List<Examen> examenList=examenRepository.findAll();
        return examenList.stream().map(ExamenBuilder::toExamenDetailsDTO).collect(Collectors.toList());
    }

    /**
     * Updates an existing Examen entity based on the provided ExamenDetailsDTO.
     *
     * @param examenDetailsDTO The DTO containing updated details of the Examen.
     * @throws RuntimeException if the Examen with the given ID does not exist.
     */
    @Override
    public void updateExamen(ExamenDetailsDTO examenDetailsDTO) {
        Optional<Examen> optionalExamen = examenRepository.findById(examenDetailsDTO.getExamen_id());
        if (!optionalExamen.isPresent()) {
            LOGGER.error("Examen with id {} was not found in db", examenDetailsDTO.getExamen_id());
            throw new RuntimeException(Examen.class.getSimpleName() + " with id: " + examenDetailsDTO.getExamen_id() + " not found");
        }

        Examen examen = optionalExamen.get(); // Retrieve the user from Optional

        // Update the user fields with values from userDetailsDTO
        //examen.setNota_examen(examenDetailsDTO.getNota_examen());
        examen.setExamen_subject(examenDetailsDTO.getExamen_subject());
       // examen.setId(examenDetailsDTO.getId());
        examen.setMaterie(examenDetailsDTO.getMaterie());
      //  examen.setPrezente(examenDetailsDTO.getPrezente());



        // Save the updated user
        examenRepository.save(examen);

        LOGGER.info("Examen with id {} was updated in db", examen.getExamen_id());
    }
    /**
     * Deletes an Examen entity by its ID.
     *
     * @param examenId The ID of the Examen to delete.
     * @throws RuntimeException if the Examen with the given ID does not exist.
     */
    @Override
    public void deleteExamen(Long examenId) {
        Optional<Examen> optionalExamen = examenRepository.findById(examenId);
        if (!optionalExamen.isPresent()) {
            LOGGER.error("Examen with id {} was not found in db", examenId);
            throw new RuntimeException(Examen.class.getSimpleName() + " with id: " + examenId);
        }

        examenRepository.delete(optionalExamen.get());
       // LOGGER.info("Examen with id {} was deleted from db", examenId);
    }
}