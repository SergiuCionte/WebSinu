package com.PSapplication.PSapplication.Controllers;


import com.PSapplication.PSapplication.DTOs.*;
import com.PSapplication.PSapplication.Services.ExamenService;
import com.PSapplication.PSapplication.Services.MaterieService;
import com.PSapplication.PSapplication.Services.UserService;
import com.PSapplication.PSapplication.Validators.ExamenValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller class for managing Examen entities.
 */
@RestController
@AllArgsConstructor

public class ExamenController {

    private final ExamenService examenService;

    @Autowired
    private ExamenValidator examenValidator;

    /**
     * Displays the page for creating an Examen.
     *
     * @return A ModelAndView object representing the createExamen page.
     */
    @GetMapping("/createPageExamen")
    public ModelAndView showCreatePageExamen() {
        return new ModelAndView("createExamen");
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
     * Creates a new Examen entity.
     *
     * @param examenDetailsDTO The details of the Examen to be created.
     * @return A ModelAndView object representing the createExamen page with appropriate messages.
     */

    @PostMapping("/create-examen")
    public ModelAndView createExamen(ExamenDetailsDTO examenDetailsDTO) {
        ModelAndView modelAndView = new ModelAndView("createExamen");
        try {
            if (
                    examenValidator.validateExamenName(examenDetailsDTO.getExamen_subject()))

                  {
                examenService.createExamen(examenDetailsDTO);
            } else {
                modelAndView.addObject("errorMessage", "Invalid examen details provided.");
            }
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error: Materie not found or materie has already an examen.");
        }
            // Handle the exception here

        return modelAndView;
    }

    /**
     * Retrieves all Examene entities.
     *
     * @return A ModelAndView object representing the view_all_examene page with the list of all Examene entities.
     */
    @GetMapping("/view-all-examene")
    public ModelAndView getAllExamene() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_examene");
        modelAndView.addObject("examene", examenService.getAllExams());


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
     * Deletes an Examen entity by its ID.
     *
     * @param examenIdToDelete The ID of the Examen to be deleted.
     * @return A ModelAndView object representing the info page with appropriate messages.
     */
    @PostMapping("/delete-examen")
    public ModelAndView deleteExamen(Long examenIdToDelete) {
        ModelAndView modelAndView = new ModelAndView("info");
       // try {
            examenService.deleteExamen(examenIdToDelete);
        modelAndView.addObject("errorMessage", "Error: Examenul este asignat unei materii");
            return modelAndView;
       // } catch (DataIntegrityViolationException e) {
            // Catch DataIntegrityViolationException caused by foreign key constraint violation
            // Log the error or handle it as needed
            //e.printStackTrace(); // You can replace this with proper logging


           // modelAndView.addObject("errorMessage", "Cannot delete materie because it is still referenced by other entities.");
           // return modelAndView;
        //}
    }

    /**
     * Displays the page for editing an Examen.
     *
     * @param examenId The ID of the Examen to be edited.
     * @return A ModelAndView object representing the edit_examen page with the details of the selected Examen.
     */
    @GetMapping("/edit-examen")
    public ModelAndView showEditExamenPage(@RequestParam("examenId") Long examenId) {
        ModelAndView modelAndView = new ModelAndView("edit_examen"); // Assuming the HTML file is named edit_user.html
        ExamenDetailsDTO examenDetailsDTO = examenService.getExamenById(examenId);
        modelAndView.addObject("examen", examenDetailsDTO);
        return modelAndView;
    }

    // Controller method to save updated user details

    /**
     * Saves the updated details of an Examen entity.
     *
     * @param examenDetailsDTO The updated details of the Examen to be saved.
     * @return A ModelAndView object representing the createExamen page with appropriate messages.
     */

    @PostMapping("/save-updated-examen")
    public ModelAndView saveUpdatedExamen(ExamenDetailsDTO examenDetailsDTO) {
        ModelAndView modelAndView = new ModelAndView("createExamen");
        try {
            examenService.updateExamen(examenDetailsDTO);
        } catch (DataIntegrityViolationException e) {
            modelAndView.addObject("errorMessage", "Error: The Materie ID provided does not exist.");
            // Handle the exception here
        } catch (JpaObjectRetrievalFailureException e) {
            modelAndView.addObject("errorMessage", "Error: Materie not found.");
            // Handle the exception here
        } catch (EntityNotFoundException e) {
            modelAndView.addObject("errorMessage", "Error: Examen details not found.");
            // Handle other entity not found exceptions if needed
        }
        return modelAndView;
    }

}
