package com.microservice.microservice.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservice.microservice.DTO.CalificativDto;
import com.microservice.microservice.DTO.NoteStudentDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {


    private ObjectMapper objectMapper;



    @Autowired
    private JavaMailSender javaMailSender;

    public class EmailTemplateLoader {
        public String loadTemplate(String filePath) {
            try {
                return new String(Files.readAllBytes(Paths.get(filePath)));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override


    public void sendEmail(NoteStudentDto noteStudentDto) {
        try {
            // Convert EmailRequestDto to JSON string
            String jsonPayload = objectMapper.writeValueAsString(noteStudentDto);

            // Create MimeMessage
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set email properties
            helper.setFrom("sergiucionte2003@gmail.com", "SINU");
            helper.setTo("sergiuciontilici@gmail.com");
            helper.setSubject("👤 User creat 🎉");

            String textWithEmojis = "Un nou utilizator a fost creat cu succes pentru " + noteStudentDto.getUsername() + ". Adresa de email asociată este: " + noteStudentDto.getEmail() + " 🎉😊";

// Setăm textul, inclusiv emoji-urile
            helper.setText(textWithEmojis, true);


// Setăm textul, inclusiv emoji-urile
            helper.setText(textWithEmojis, true);

            helper.addAttachment(" file",new File(noteStudentDto.getPath()));


            // Send email
            javaMailSender.send(message);

            // Save the email entity with status "SENT" initially


        } catch (JsonProcessingException | MessagingException | UnsupportedEncodingException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void send(CalificativDto calificativDto) {
        try {
            // Convert EmailRequestDto to JSON string
            String jsonPayload = objectMapper.writeValueAsString(calificativDto);

            // Create MimeMessage
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set email properties
            helper.setFrom("sergiucionte2003@gmail.com", "SINU");
            helper.setTo("sergiuciontilici@gmail.com");
            helper.setSubject("Dragă " + calificativDto.getUsername() + " ⭐⭐⭐");

            helper.setText("<p style=\"font-family: Arial, sans-serif; color: #333333;\">Stimate domnule/doamnă " + calificativDto.getUsername() + ",</p>" +
                    "<p style=\"font-family: Arial, sans-serif; color: #333333;\">În urma evaluării lucrării dvs. cu numărul " + calificativDto.getId() + ", vă aducem la cunoștință că ați obținut calificativul " + calificativDto.getCalificativ() + ".</p>" +
                    "<p style=\"font-family: Arial, sans-serif; color: #333333;\">Vă mulțumim pentru efortul depus și vă felicităm pentru rezultatul obținut.</p>" +
                    "<p style=\"font-family: Arial, sans-serif; color: #333333;\">Cu respect,</p>" +
                    "<p style=\"font-family: Arial, sans-serif; color: #333333;\">Echipa Academică &#128515;&#128516;</p>", true);


            // Send email
            javaMailSender.send(message);

            // Save the email entity with status "SENT" initially


        } catch (JsonProcessingException | MessagingException | UnsupportedEncodingException e) {
            // Handle exception
            e.printStackTrace();
        }
    }
}
