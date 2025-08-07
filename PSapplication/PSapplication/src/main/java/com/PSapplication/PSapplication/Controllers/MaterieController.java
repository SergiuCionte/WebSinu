package com.PSapplication.PSapplication.Controllers;


import com.PSapplication.PSapplication.DTOs.MaterieDTO;
import com.PSapplication.PSapplication.DTOs.MaterieDetailsDTO;
import com.PSapplication.PSapplication.DTOs.UserDTO;
import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.Examen;
import com.PSapplication.PSapplication.Entities.Materie;
import com.PSapplication.PSapplication.Entities.NotaExamen;
import com.PSapplication.PSapplication.Services.MaterieService;
import com.PSapplication.PSapplication.Services.UserService;
import com.PSapplication.PSapplication.Validators.MaterieValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller class for managing Materie entities.
 */
@RestController
@AllArgsConstructor

public class MaterieController {

    private final MaterieService materieService;
    @Autowired
    private final MaterieValidator materieValidator;




    /**
     * Displays the page for creating a Materie.
     *
     * @return A ModelAndView object representing the createMaterie page.
     */
    @GetMapping("/createPageMaterie")
    public ModelAndView showCreatePageMaterie() {
        return new ModelAndView("createMaterie");
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
     * Creates a new Materie entity.
     *
     * @param materieDetailsDTO The details of the Materie to be created.
     * @return A ModelAndView object representing the createMaterie page with appropriate messages.
     */
    @PostMapping("/create-materie")
    public ModelAndView createMaterie(MaterieDetailsDTO materieDetailsDTO) {
        ModelAndView modelAndView = new ModelAndView("createMaterie");

        try {
            if (materieValidator.validateMaterieName(materieDetailsDTO.getNume_materie())
            ) {
                materieService.createMaterie(materieDetailsDTO);
            } else {
                modelAndView.addObject("errorMessage", "Invalid Materie details provided.");
            }
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error occurred while creating Materie.");
            // Handle other exceptions if needed
        }

        return modelAndView;
    }

    /**
     * Retrieves all Materie entities.
     *
     * @return A ModelAndView object representing the view_all_materie_default page with the list of all Materie entities.
     */
    @GetMapping("/view-all-materie")
    public ModelAndView getAllMaterie() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_materie_default");
        modelAndView.addObject("materii", materieService.getAllMateries());
        return modelAndView;
    }

    /**
     * Retrieves all users associated with a Materie.
     *
     * @param idProf The ID of the Materie to retrieve associated users for.
     * @return A ModelAndView object representing the view_all_materie page with the list of users associated with the specified Materie.
     */
    @GetMapping("/view-all-usersfrom-materie")
    public ModelAndView getAllUsersMaterie(@RequestParam UUID idProf) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_materie");
        modelAndView.addObject("users", materieService.getAllUsersFromMaterie());
        modelAndView.addObject("materii", materieService.getAllMateries());
        modelAndView.addObject("idProf", idProf);
        return modelAndView;
    }

    @GetMapping("/materii")
    public ModelAndView getAllUsersMaterie(@RequestParam UUID idProf, @RequestParam(value = "sort", required = false) String sort) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_materie");

        // Fetch users and materii from the service
        List<UserDetailsDTO> users = materieService.getAllUsersFromMaterie();

        // Initialize the list to hold sorted materii
        List<Materie> sortedMaterieList = new ArrayList<>();

        // Sort materii and calificativ_examen if requested
        users.stream()
                .filter(user -> user.getId().equals(idProf))
                .forEach(user -> {
                    if ("STUDENT".equals(user.getRole())) {
                        List<Materie> materieList = new ArrayList<>(user.getMaterieList());
                        if ("alphabetical".equals(sort)) {
                            materieList.sort(Comparator.comparing(Materie::getNume_materie));
                        }
                        // Sort calificativ_examen within the single examen of each materie
                        materieList.forEach(materie -> {
                            Examen examen = materie.getExamen();
                            if (examen != null) {
                                List<NotaExamen> notaExamenList = new ArrayList<>(examen.getNotaExamenList());
                                notaExamenList.sort(Comparator.comparing(NotaExamen::getCalificativ_examen));
                                examen.setNotaExamenList(notaExamenList);
                            }
                        });
                        user.setMaterieList(new HashSet<>(materieList));
                        sortedMaterieList.addAll(materieList);
                    }
                });

        modelAndView.addObject("users", users);
        modelAndView.addObject("materii", sortedMaterieList);
        modelAndView.addObject("idProf", idProf);
        return modelAndView;
    }


    @GetMapping("/materiii")
    public ModelAndView getAllUserssMaterie(@RequestParam UUID idProf, @RequestParam(value = "sort", required = false) String sort) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_materie");

        // Fetch users and materii from the service
        List<UserDetailsDTO> users = materieService.getAllUsersFromMaterie();

        // Initialize the list to hold sorted materii
        List<Materie> sortedMaterieList = new ArrayList<>();

        // Sort materii and calificativ_examen if requested
        users.stream()
                .filter(user -> user.getId().equals(idProf))
                .forEach(user -> {
                    if ("STUDENT".equals(user.getRole())) {
                        List<Materie> materieList = new ArrayList<>(user.getMaterieList());
                        if ("alphabetical".equals(sort)) {
                            materieList.sort(Comparator.comparing(Materie::getNume_materie));
                        }
                        // Sort calificativ_examen within the single examen of each materie
                        materieList.forEach(materie -> {
                            Examen examen = materie.getExamen();
                            if (examen != null) {
                                List<NotaExamen> notaExamenList = new ArrayList<>(examen.getNotaExamenList());
                                if ("descending".equals(sort)) {
                                    notaExamenList.sort(Comparator.comparing(NotaExamen::getCalificativ_examen).reversed());
                                } else {
                                    notaExamenList.sort(Comparator.comparing(NotaExamen::getCalificativ_examen));
                                }
                                examen.setNotaExamenList(notaExamenList);
                            }
                        });
                        user.setMaterieList(new HashSet<>(materieList));
                        sortedMaterieList.addAll(materieList);
                    }
                });

        modelAndView.addObject("users", users);
        modelAndView.addObject("materii", sortedMaterieList);
        modelAndView.addObject("idProf", idProf);
        return modelAndView;
    }

    @GetMapping("/searchMaterii")
    public ModelAndView searchMaterii(@RequestParam UUID idProf, @RequestParam(value = "search", required = false) String search) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_materie");

        // Fetch users and materii from the service
        List<UserDetailsDTO> users = materieService.getAllUsersFromMaterie();

        // Initialize the list to hold filtered materii
        List<Materie> filteredMaterieList = new ArrayList<>();

        // Filter materii based on search criteria
        users.stream()
                .filter(user -> user.getId().equals(idProf))
                .forEach(user -> {
                    if ("STUDENT".equals(user.getRole())) {
                        List<Materie> materieList = new ArrayList<>(user.getMaterieList());
                        if (search != null && !search.isEmpty()) {
                            materieList = materieList.stream()
                                    .filter(materie -> materie.getNume_materie().equalsIgnoreCase(search))
                                    .collect(Collectors.toList());
                        }
                        user.setMaterieList(new HashSet<>(materieList));
                        filteredMaterieList.addAll(materieList);
                    }
                });

        modelAndView.addObject("users", users);
        modelAndView.addObject("materii", filteredMaterieList);
        modelAndView.addObject("idProf", idProf);
        modelAndView.addObject("search", search);
        return modelAndView;
    }


    @GetMapping("/materiiWithNullExamen")
    public ModelAndView getMateriiWithNullExamen(@RequestParam UUID idProf) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_materie");

        // Fetch users and materii from the service
        List<UserDetailsDTO> users = materieService.getAllUsersFromMaterie();

        // Initialize the list to hold filtered materii
        List<Materie> filteredMaterieList = new ArrayList<>();

        // Filter materii with null examen
        users.stream()
                .filter(user -> user.getId().equals(idProf))
                .forEach(user -> {
                    if ("STUDENT".equals(user.getRole())) {
                        List<Materie> materieList = new ArrayList<>(user.getMaterieList());
                        materieList = materieList.stream()
                                .filter(materie -> materie.getExamen() == null)
                                .collect(Collectors.toList());
                        user.setMaterieList(new HashSet<>(materieList));
                        filteredMaterieList.addAll(materieList);
                    }
                });

        modelAndView.addObject("users", users);
        modelAndView.addObject("materii", filteredMaterieList);
        modelAndView.addObject("idProf", idProf);
        return modelAndView;
    }








    @GetMapping("/view-all-usersfrom-materie-prof")
    public ModelAndView getAllUsersMaterieProf(@RequestParam UUID idProf) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_materie_profesor");
        modelAndView.addObject("users", materieService.getAllUsersFromMaterie());
        modelAndView.addObject("materii", materieService.getAllMateries());
        modelAndView.addObject("idProf", idProf);
        return modelAndView;
    }
    // Mapping to delete user

    /**
     * Deletes a Materie entity by its ID.
     *
     * @param materieIdToDelete The ID of the Materie to be deleted.
     * @return A ModelAndView object representing the info page with appropriate messages.
     */
    @PostMapping("/delete-materie")
    public ModelAndView deleteMaterie(Long materieIdToDelete) {
        ModelAndView modelAndView = new ModelAndView("info");
        try {
            materieService.deleteMaterie(materieIdToDelete);
            modelAndView.addObject("errorMessage", "Materia s-a sters cu succes");
            return modelAndView;
        } catch (DataIntegrityViolationException e) {
            // Catch DataIntegrityViolationException caused by foreign key constraint violation
            // Log the error or handle it as needed
            //e.printStackTrace(); // You can replace this with proper logging


            modelAndView.addObject("errorMessage", "Nu se poate sterge materia deoarece mai sunt studenti asignati");
            return modelAndView;
        }

    }

    /**
     * Displays the page for editing a Materie.
     *
     * @param materieId The ID of the Materie to be edited.
     * @return A ModelAndView object representing the edit_materie page with the details of the selected Materie.
     */
    @GetMapping("/edit-materie")
    public ModelAndView showEditMateriePage(@RequestParam("materieId") Long materieId) {
        ModelAndView modelAndView = new ModelAndView("edit_materie"); // Assuming the HTML file is named edit_user.html
        MaterieDetailsDTO materieDetailsDTO = materieService.getMaterieById(materieId);
        modelAndView.addObject("materie", materieDetailsDTO);
        return modelAndView;
    }

    /**
     * Saves the updated details of a Materie entity.
     *
     * @param materieDetailsDTO The updated details of the Materie to be saved.
     * @return A ModelAndView object representing the view_all_materie_default page with the updated list of Materie entities.
     */
    // Controller method to save updated user details
    @PostMapping("/save-updated-materie")
    public ModelAndView saveUpdatedMaterie(MaterieDetailsDTO materieDetailsDTO) {
        materieService.updateMaterie(materieDetailsDTO);
        return getAllMaterie();
    }
}
