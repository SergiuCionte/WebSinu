package com.PSapplication.PSapplication.config;

import com.PSapplication.PSapplication.DTOs.CalificativDto;
import com.PSapplication.PSapplication.DTOs.NotaExamenDetailsDTO;
import com.PSapplication.PSapplication.DTOs.NoteStudentDto;
import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Repositories.NotaExamenRepository;
import com.PSapplication.PSapplication.Repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@AllArgsConstructor
@Service

public class MessageProducer {
    private final RestTemplate restTemplate;

    private final RabbitTemplate rabbitTemplate;

    private final UserRepository userRepository;

    private final NotaExamenRepository notaExamenRepository;


    private final ObjectMapper objectMapper;
    private final String authToken = "1ceb849e-53ea-40de-aa03-e9920e334f0-4db5ac0d-afe9-4e81-8f50-6c64bf0a0fbc";



    public void sendMessage(String username, NotaExamenDetailsDTO notaExamenDetailsDTO){

        if (notaExamenDetailsDTO == null || notaExamenDetailsDTO.getUser() == null||notaExamenDetailsDTO.getCalificativ_examen()==null) {
            System.out.println("NotaExamenDetailsDTO sau utilizatorul asociat este null.");
            return;
        }
       // NoteStudentDto noteStudentDto=new NoteStudentDto();
        CalificativDto calificativDto=new CalificativDto();
        calificativDto.setTitle("Update catalog note");
        calificativDto.setDescription("Notele tale sunt");
       // noteStudentDto.setId(userDetailsDTO.getId());
        calificativDto.setId(userRepository.findById(notaExamenDetailsDTO.getUser().getId()).get().getId());
        calificativDto.setUsername(userRepository.findById(notaExamenDetailsDTO.getUser().getId()).get().getUsername());
        //calificativDto.setUsername(username);
        calificativDto.setCalificativ(notaExamenDetailsDTO.getCalificativ_examen());
        //calificativDto.setExamen(notaExamenRepository.findById(notaExamenDetailsDTO.getNota_examen_id()).get().getExamen().getExamen_subject());
        System.out.println(calificativDto.getUsername());
      //  noteStudentDto.setMaterieList(userDetailsDTO.getMaterieList());
        rabbitTemplate.convertAndSend("emailQueue",calificativDto);
    }


    public void sendEmailToMailApp(UserDetailsDTO userDetailsDTO,String path) {
        try {
            if (userDetailsDTO == null || userDetailsDTO.getUsername() == null  || userDetailsDTO.getEmail() == null) {
                System.out.println("Missing required parameters in UserDetailsDTO.");
                return; // sau aruncați o excepție, sau tratați altfel cazul în care lipsesc parametrii
            }
            NoteStudentDto noteStudentDto=new NoteStudentDto();
            noteStudentDto.setTitle("User creat");
            noteStudentDto.setDescription("S-a creat un user cu username-ul");
           // noteStudentDto.setId(userDetailsDTO.getId());
            noteStudentDto.setUsername(userDetailsDTO.getUsername());
           // noteStudentDto.setId(userRepository.findById(userDetailsDTO.getId()).get().getId());

            noteStudentDto.setId(userDetailsDTO.getId());
            noteStudentDto.setEmail(userDetailsDTO.getEmail());
            noteStudentDto.setPath(path);


            // Convert EmailRequestDto to JSON string
            String jsonPayload = objectMapper.writeValueAsString(noteStudentDto);


            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + authToken); // Include the authorization token here

            // Set up request entity
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

            // Make the HTTP POST request
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/email/send", // URL of your email app
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // Handle response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Email successfully sent!");
            } else {
                System.out.println("Failed to send email. Response: " + response.getBody());
            }
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            e.printStackTrace();
        }
    }
}
