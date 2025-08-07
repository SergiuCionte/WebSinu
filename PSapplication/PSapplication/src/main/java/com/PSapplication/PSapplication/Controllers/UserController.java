package com.PSapplication.PSapplication.Controllers;

import com.PSapplication.PSapplication.DTOs.ExamenDTO;
import com.PSapplication.PSapplication.DTOs.MaterieDTO;
import com.PSapplication.PSapplication.DTOs.NoteStudentDto;
import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.Examen;
import com.PSapplication.PSapplication.Entities.Materie;
import com.PSapplication.PSapplication.Entities.NotaExamen;
import com.PSapplication.PSapplication.Generator.CsvFileGenerator;
import com.PSapplication.PSapplication.Generator.PdfFileGenerator;
import com.PSapplication.PSapplication.Generator.Student;
import com.PSapplication.PSapplication.Generator.TxtFileGenerator;
import com.PSapplication.PSapplication.Services.UserService;
import com.PSapplication.PSapplication.Validators.UserValidator;
import com.PSapplication.PSapplication.config.MessageProducer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//@RequestMapping("api/users")

/**
 * Controller class for managing User entities.
 */
@Controller
//@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MessageProducer messageProducer;


    private Student student;

    /**
     * Displays the admin page.
     *
     * @return A ModelAndView object representing the admin page.
     */
    @GetMapping("/admin")
    public ModelAndView showAdminPage() {
        return new ModelAndView("admin");
    }


    /**
     * Displays the professor page.
     *
     * @return A ModelAndView object representing the professor page.
     */
    @GetMapping("/profesor")
    public ModelAndView showProfesorPage() {
        return new ModelAndView("profesor");
    }

    /**
     * Displays the student page.
     *
     * @return A ModelAndView object representing the student page.
     */
    @GetMapping("/student")
    public ModelAndView showStudentPage() {
        return new ModelAndView("student");
    }


    /**
     * Displays the management page.
     *
     * @return A ModelAndView object representing the management page.
     */
    @GetMapping("/management")
    public ModelAndView showManagePage() {
        return new ModelAndView("view_all_users");
    }

    /**
     * Displays the page for creating a User.
     *
     * @return A ModelAndView object representing the createUser page.
     */

    @GetMapping("/createPage")
    public ModelAndView showCreatePage() {
        return new ModelAndView("createUser");
    }




    /*
    @GetMapping("{id}")
    public ResponseEntity<UserDetailsDTO> getStudentById(@PathVariable("id") Long userId){
        UserDetailsDTO userDetailsDTO=userService.getUserById(userId);
        return new ResponseEntity<>(userDetailsDTO,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getUsers() {
        List<UserDetailsDTO> dtos = userService.getAllUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Long> insertUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        Long userId=userService.createUser(userDetailsDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);



    }

*/


    /**
     * Creates a new User entity.
     *
     * @param userDetailsDTO The details of the User to be created.
     * @return A ModelAndView object representing the createUser page with appropriate messages.
     */
    @PostMapping("/create-user")
    public ModelAndView createUser(UserDetailsDTO userDetailsDTO) {
        ModelAndView modelAndView = new ModelAndView("createUser");
        try {
            if (userValidator.validateEmail(userDetailsDTO.getEmail()) &&
                    userValidator.validateUsername(userDetailsDTO.getUsername()) &&
                    userValidator.validateRole(userDetailsDTO.getRole())) {
                userService.createUser(userDetailsDTO);
                modelAndView.addObject("errorMessage", "User Created.");
                //messageProducer.sendMessage(userDetailsDTO);
                //messageProducer.sendEmailToMailApp(userDetailsDTO);

            } else {
                modelAndView.addObject("errorMessage", "Invalid user details provided.");
            }
        } catch (JpaObjectRetrievalFailureException e) {
            modelAndView.addObject("errorMessage", "Error: Materie  not found.");
            // Handle the exception here
        } catch (EntityNotFoundException e) {
            modelAndView.addObject("errorMessage", "Error: User details not found.");
            // Handle other entity not found exceptions if needed
        }
        return modelAndView;
    }


    /**
     * Retrieves all User entities.
     *
     * @return A ModelAndView object representing the view_all_users page with the list of all User entities.
     */

    @GetMapping("/view-all-users")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_users");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }
    // Mapping to delete user

    /**
     * Deletes a User entity by its ID.
     *
     * @param userIdToDelete The ID of the User to be deleted.
     * @return A ModelAndView object representing the view_all_users page with appropriate messages.
     */
    @PostMapping("/delete-user")
    public ModelAndView deleteUser( UUID userIdToDelete) {
        // Delete user from the database using the service

        userService.deleteUser(userIdToDelete);
        return getAllUsers();
    }

    @PostMapping("/generate-csv")
    public ModelAndView generateCsv(@RequestParam("userIdStudent") UUID userIdStudent) {
        ModelAndView modelAndView = new ModelAndView("info");

        // Get user details by ID
        UserDetailsDTO userDetailsDTO = userService.getUserById(userIdStudent);

        // Generate PDF file
        Student student = new Student(new CsvFileGenerator());
        File pdfFile = student.generateFile(userDetailsDTO);

        // Send email with PDF file attachment
        try {
            // Create NoteStudentDto with user details
            NoteStudentDto noteStudentDto = new NoteStudentDto();
            noteStudentDto.setUsername(userDetailsDTO.getUsername());
            noteStudentDto.setEmail(userDetailsDTO.getEmail());

            // Send email with PDF file attachment
            messageProducer.sendEmailToMailApp(userDetailsDTO, pdfFile.getAbsolutePath());

            // Display success message
            modelAndView.addObject("errorMessage", "Csv File Generated and sent via email");
        } catch (Exception e) {
            // Handle exception
            modelAndView.addObject("errorMessage", "Error generating Csv file or sending email");
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/generate-txt")
    public ModelAndView generateTxt(@RequestParam("userIdStudentt") UUID userIdStudentt) {
        ModelAndView modelAndView = new ModelAndView("info");

        // Get user details by ID
        UserDetailsDTO userDetailsDTO = userService.getUserById(userIdStudentt);

        // Generate PDF file
        Student student = new Student(new TxtFileGenerator());
        File pdfFile = student.generateFile(userDetailsDTO);

        // Send email with PDF file attachment
        try {
            // Create NoteStudentDto with user details
            NoteStudentDto noteStudentDto = new NoteStudentDto();
            noteStudentDto.setUsername(userDetailsDTO.getUsername());
            noteStudentDto.setEmail(userDetailsDTO.getEmail());

            // Send email with PDF file attachment
            messageProducer.sendEmailToMailApp(userDetailsDTO, pdfFile.getAbsolutePath());

            // Display success message
            modelAndView.addObject("errorMessage", "Txt File Generated and sent via email");
        } catch (Exception e) {
            // Handle exception
            modelAndView.addObject("errorMessage", "Error generating Txt file or sending email");
            e.printStackTrace();
        }

        return modelAndView;
    }

    @PostMapping("/generate-pdf")
    public ModelAndView generatePdf(@RequestParam("userIdStudenttt") UUID userIdStudenttt) {
        ModelAndView modelAndView = new ModelAndView("info");

        // Get user details by ID
        UserDetailsDTO userDetailsDTO = userService.getUserById(userIdStudenttt);

        // Generate PDF file
        Student student = new Student(new PdfFileGenerator());
        File pdfFile = student.generateFile(userDetailsDTO);

        // Send email with PDF file attachment
        try {
            // Create NoteStudentDto with user details
            NoteStudentDto noteStudentDto = new NoteStudentDto();
            noteStudentDto.setUsername(userDetailsDTO.getUsername());
            noteStudentDto.setEmail(userDetailsDTO.getEmail());

            // Send email with PDF file attachment
            messageProducer.sendEmailToMailApp(userDetailsDTO, pdfFile.getAbsolutePath());

            // Display success message
            modelAndView.addObject("errorMessage", "Pdf File Generated and sent via email");
        } catch (Exception e) {
            // Handle exception
            modelAndView.addObject("errorMessage", "Error generating PDF file or sending email");
            e.printStackTrace();
        }

        return modelAndView;
    }

    /**
     * Displays the page for editing a User.
     *
     * @param userId The ID of the User to be edited.
     * @return A ModelAndView object representing the edit_user page with the details of the selected User.
     */

    @GetMapping("/edit-user")
    public ModelAndView showEditUserPage(@RequestParam("userId") UUID userId) {
        ModelAndView modelAndView = new ModelAndView("edit_user"); // Assuming the HTML file is named edit_user.html
        UserDetailsDTO userDetailsDTO = userService.getUserById(userId);
        modelAndView.addObject("user", userDetailsDTO);
        return modelAndView;
    }



    @PostMapping("/calculate-median")
    public ModelAndView calculateMedian(@RequestParam("idProf") UUID idProf) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info");

        try {
            // Retrieve UserDetailsDTO by idProf
            UserDetailsDTO userDetailsDTO = userService.getUserById(idProf);

            // Extract calificativ_examen values from UserDetailsDTO
            List<Long> calificativList = new ArrayList<>();
            long sum = 0;

            for (Materie materie : userDetailsDTO.getMaterieList()) {
                if (materie.getExamen() != null) {
                    for (NotaExamen notaExamen : materie.getExamen().getNotaExamenList()) {
                        if (notaExamen.getCalificativ_examen() != null) {
                            calificativList.add(notaExamen.getCalificativ_examen());
                            sum += notaExamen.getCalificativ_examen();
                        }
                    }
                }
            }

            // Calculate the mean
            if (!calificativList.isEmpty()) {
                double mean = (double) sum / calificativList.size();
                modelAndView.addObject("errorMessage", "Mean of Calificativ Examen: " + mean);
                System.out.println(mean);
            } else {
                modelAndView.addObject("errorMessage", "No calificativ_examen values found.");
                System.out.println("No calificativ_examen values found.");
            }

        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error calculating mean");
            e.printStackTrace();
        }

        return modelAndView;
    }


    private double calculateMedian(List<Long> calificativList) {
        if (calificativList == null || calificativList.isEmpty()) {
            return 0;
        }

        int size = calificativList.size();
        calificativList.sort(Long::compareTo);

        if (size % 2 == 0) {
            return (calificativList.get(size / 2 - 1) + calificativList.get(size / 2)) / 2.0;
        } else {
            return calificativList.get(size / 2);
        }
    }

    // Controller method to save updated user details

    /**
     * Saves the updated details of a User entity.
     *
     * @param userDetailsDTO The updated details of the User to be saved.
     * @return A ModelAndView object representing the info page with appropriate messages.
     */
    @PostMapping("/save-updated-user")
    public ModelAndView saveUpdatedUser(UserDetailsDTO userDetailsDTO) {
        ModelAndView modelAndView = new ModelAndView("info");
        try {
            if (userValidator.validateEmail(userDetailsDTO.getEmail()) &&
                    userValidator.validateUsername(userDetailsDTO.getUsername()) &&
                    userValidator.validateRole(userDetailsDTO.getRole())) {
                userService.updateUser(userDetailsDTO);
                modelAndView.addObject("errorMessage", "User updated.");
            } else {
                modelAndView.addObject("errorMessage", "Invalid user details provided.");
            }

            //userService.updateUser(userDetailsDTO);

        } catch (JpaObjectRetrievalFailureException e) {
            modelAndView.addObject("errorMessage", "Error: Materie  not found.");
            // Handle the exception here
        } catch (EntityNotFoundException e) {
            modelAndView.addObject("errorMessage", "Error: User details not found.");
            // Handle other entity not found exceptions if needed
        }
        return modelAndView;


    }

}
