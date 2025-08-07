package com.PSapplication.PSapplication.Services;

import com.PSapplication.PSapplication.DTOs.Builders.NotaExamenBuilder;
import com.PSapplication.PSapplication.DTOs.NotaExamenDetailsDTO;
import com.PSapplication.PSapplication.Entities.NotaExamen;
import com.PSapplication.PSapplication.Repositories.NotaExamenRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service implementation for managing NotaExamen entities.
 */
@Service
@AllArgsConstructor
public class NotaExamenServiceImpl implements NotaExamenService {

    private static final Logger LOGGER= LoggerFactory.getLogger(ExamenService.class);
    private final NotaExamenRepository notaExamenRepository;


    /**
     * Creates a new NotaExamen entity.
     *
     * @param notaExamenDetailsDTO The details of the NotaExamen to be created.
     * @return The ID of the newly created NotaExamen entity.
     */

    @Override
    public Long createNotaExamen(NotaExamenDetailsDTO notaExamenDetailsDTO) {
        NotaExamen notaExamen = NotaExamenBuilder.toEntity(notaExamenDetailsDTO);
        notaExamen=notaExamenRepository.save(notaExamen);
        LOGGER.info("Nota Examen with id {} was inserted in db",notaExamen.getNota_examen_id());
        return notaExamen.getNota_examen_id();
    }

    /**
     * Retrieves a NotaExamen entity by its ID.
     *
     * @param id The ID of the NotaExamen to retrieve.
     * @return The NotaExamenDetailsDTO corresponding to the specified ID.
     * @throws RuntimeException if the NotaExamen with the given ID does not exist.
     */
    @Override
    public NotaExamenDetailsDTO getNotaExamenById(Long id) {
        Optional<NotaExamen> notaExamenOptional = notaExamenRepository.findById(id);
        if (!notaExamenOptional.isPresent()) {
            LOGGER.error("NotaExamen with id {} was not found in db", id);
            throw new RuntimeException(NotaExamen.class.getSimpleName() + " with id: " + id);
        }
        return NotaExamenBuilder.toNotaExamenDetailsDTO(notaExamenOptional.get());

    }

    /**
     * Retrieves a list of all NotaExamen entities.
     *
     * @return A list of NotaExamenDetailsDTO objects representing all NotaExamen entities.
     */
    @Override
    public List<NotaExamenDetailsDTO> getAllNotaExamen() {
        List<NotaExamen> notaExamenList=notaExamenRepository.findAll();
        return notaExamenList.stream().map(NotaExamenBuilder::toNotaExamenDetailsDTO).collect(Collectors.toList());
    }

    /**
     * Updates an existing NotaExamen entity.
     *
     * @param notaExamenDetailsDTO The details of the NotaExamen to be updated.
     * @throws RuntimeException if the NotaExamen with the given ID does not exist.
     */

    @Override
    public void updateNotaExamen(NotaExamenDetailsDTO notaExamenDetailsDTO) {
        Optional<NotaExamen> optionalNotaExamen = notaExamenRepository.findById(notaExamenDetailsDTO.getNota_examen_id());
        if (!optionalNotaExamen.isPresent()) {
            LOGGER.error("NotaExamen with id {} was not found in db", notaExamenDetailsDTO.getNota_examen_id());
            throw new RuntimeException(NotaExamen.class.getSimpleName() + " with id: " + notaExamenDetailsDTO.getNota_examen_id()+ " not found");
        }

        NotaExamen notaExamen = optionalNotaExamen.get(); // Retrieve the user from Optional

        // Update the user fields with values from userDetailsDTO
        notaExamen.setCalificativ_examen(notaExamenDetailsDTO.getCalificativ_examen());
        notaExamen.setExamen(notaExamenDetailsDTO.getExamen());
        notaExamen.setUser(notaExamenDetailsDTO.getUser());






        // Save the updated user
      notaExamenRepository.save(notaExamen);

        LOGGER.info("NotaExamen with id {} was updated in db", notaExamen.getNota_examen_id());
    }

    /**
     * Deletes a NotaExamen entity by its ID.
     *
     * @param notaExamenId The ID of the NotaExamen to be deleted.
     * @throws RuntimeException if the NotaExamen with the given ID does not exist.
     */
    @Override
    public void deleteNotaExamen(Long notaExamenId) {
        Optional<NotaExamen> optionalNotaExamen = notaExamenRepository.findById(notaExamenId);
        if (!optionalNotaExamen.isPresent()) {
            LOGGER.error("NotaExamen with id {} was not found in db", notaExamenId);
            throw new RuntimeException(NotaExamen.class.getSimpleName() + " with id: " + notaExamenId);
        }
        notaExamenRepository.delete(optionalNotaExamen.get());

        LOGGER.info("NotaExamen with id {} was deleted from db", notaExamenId);
    }
}