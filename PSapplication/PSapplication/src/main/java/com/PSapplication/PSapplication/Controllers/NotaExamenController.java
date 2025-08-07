package com.PSapplication.PSapplication.Controllers;

import com.PSapplication.PSapplication.DTOs.NotaExamenDetailsDTO;
import com.PSapplication.PSapplication.Services.NotaExamenService;
import com.PSapplication.PSapplication.Validators.NotaExamenValidator;
import com.PSapplication.PSapplication.config.MessageProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class for managing NotaExamen entities.
 */
@RestController
@AllArgsConstructor
public class NotaExamenController {

    private final NotaExamenService notaExamenService;
    @Autowired
    private NotaExamenValidator notaExamenValidator;

    @Autowired
    private MessageProducer messageProducer;


    /**
     * Displays the page for creating a NotaExamen.
     *
     * @return A ModelAndView object representing the createNotaExamen page.
     */
    @GetMapping("/createPageNotaExamen")
    public ModelAndView showCreatePageNotaExamen() {
        return new ModelAndView("createNotaExamen");
    }

    /*
    @GetMapping
    public ResponseEntity<List<MaterieDTO>> getMaterii() {
        List<MaterieDTO> dtos = materieService.getAllMateries();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Long> insertMaterie(@RequestBody MaterieDetailsDTO materieDetailsDTO) {
        Long materieId=materieService.createMaterie(materieDetailsDTO);
        return new ResponseEntity<>(materieId, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMaterie(@PathVariable("id") Long materieId){
        materieService.deleteMaterie(materieId);
        return new ResponseEntity<>("Materie succesfully deleted",HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MaterieDetailsDTO> getMaterieById(@PathVariable("id") Long materieId){
        MaterieDetailsDTO materieDetailsDTO=materieService.getMaterieById(materieId);
        return new ResponseEntity<>(materieDetailsDTO,HttpStatus.OK);
    }

    @PutMapping ("{id}")
    public ResponseEntity<String> updateMaterie(@PathVariable("id") Long materieId,@RequestBody MaterieDetailsDTO materieDetailsDTO){
        materieDetailsDTO.setId(materieId);

        materieService.updateMaterie(materieDetailsDTO);
        return new ResponseEntity<>("Materie successfully updated",HttpStatus.OK);
    }

     */

    /**
     * Creates a new NotaExamen entity.
     *
     * @param notaExamenDetailsDTO The details of the NotaExamen to be created.
     * @return A ModelAndView object representing the info page with appropriate messages.
     */
    @PostMapping("/create-notaexamen")
    public ModelAndView createNotaExamen(NotaExamenDetailsDTO notaExamenDetailsDTO) {
        ModelAndView modelAndView = new ModelAndView("info");
        try {
            if (
                    notaExamenValidator.validateCalificativExamen(notaExamenDetailsDTO.getCalificativ_examen())) {

                notaExamenService.createNotaExamen(notaExamenDetailsDTO);
               // System.out.println(notaExamenDetailsDTO.getUser().getUsername());
                messageProducer.sendMessage(notaExamenDetailsDTO.getUser().getUsername(),notaExamenDetailsDTO);
                modelAndView.addObject("errorMessage", "Nota Examen created.");
            } else {
                modelAndView.addObject("errorMessage", "Invalid nota examen details provided.");
            }
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Nu exista user respectiv.");
            // Handle other exceptions if needed
        }
        return modelAndView;
    }

    /**
     * Retrieves all NotaExamen entities.
     *
     * @return A ModelAndView object representing the view_all_nota_examen page with the list of all NotaExamen entities.
     */
    @GetMapping("/view-all-nota-examen")
    public ModelAndView getAllNotaExamen() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_nota_examen");
        modelAndView.addObject("noteExamene", notaExamenService.getAllNotaExamen());
        return modelAndView;
    }

    /*
    @GetMapping("/view-all-usersfrom-materie")
    public ModelAndView getAllUsersMaterie(@RequestParam String usernameProf) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_materie");
        modelAndView.addObject("users", materieService.getAllUsersFromMaterie());
        modelAndView.addObject("materii", materieService.getAllMateries());
        modelAndView.addObject("usernameProf", usernameProf);
        return modelAndView;
    }

     */
    // Mapping to delete user
    /**
     * Deletes a NotaExamen entity by its ID.
     *
     * @param NotaExamenIdToDelete The ID of the NotaExamen to be deleted.
     * @return A ModelAndView object representing the view_all_nota_examen page with appropriate messages.
     */
    @PostMapping("/delete-notaExamen")
    public ModelAndView deleteNotaExamen(Long NotaExamenIdToDelete) {
        try {
            notaExamenService.deleteNotaExamen(NotaExamenIdToDelete);
            return getAllNotaExamen();
        } catch (DataIntegrityViolationException e) {
            // Catch DataIntegrityViolationException caused by foreign key constraint violation
            // Log the error or handle it as needed
            //e.printStackTrace(); // You can replace this with proper logging

            ModelAndView modelAndView = getAllNotaExamen();
            modelAndView.addObject("errorMessage", "Cannot delete materie because it is still referenced by other entities.");
            return modelAndView;
        }
    }

    /**
     * Displays the page for editing a NotaExamen.
     *
     * @param notaExamenId The ID of the NotaExamen to be edited.
     * @return A ModelAndView object representing the edit_notaExamen page with the details of the selected NotaExamen.
     */
    @GetMapping("/edit-NotaExamen")
    public ModelAndView showEditNotaExamenPage(@RequestParam("notaExamenId") Long notaExamenId) {
        ModelAndView modelAndView = new ModelAndView("edit_notaExamen"); // Assuming the HTML file is named edit_user.html
        NotaExamenDetailsDTO notaExamenDetailsDTO = notaExamenService.getNotaExamenById(notaExamenId);
        modelAndView.addObject("notaExamen", notaExamenDetailsDTO);
        return modelAndView;
    }

    // Controller method to save updated user details

    /**
     * Saves the updated details of a NotaExamen entity.
     *
     * @param notaExamenDetailsDTO The updated details of the NotaExamen to be saved.
     * @return A ModelAndView object representing the view_all_nota_examen page with the updated list of NotaExamen entities.
     */
    @PostMapping("/save-updated-notaExamen")
    public ModelAndView saveUpdatedNotaExamen(NotaExamenDetailsDTO notaExamenDetailsDTO) {
        notaExamenService.updateNotaExamen(notaExamenDetailsDTO);

        return getAllNotaExamen();
    }
}
