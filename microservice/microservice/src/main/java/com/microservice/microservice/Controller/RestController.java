package com.microservice.microservice.Controller;


import com.microservice.microservice.DTO.MessageDto;
import com.microservice.microservice.DTO.NoteStudentDto;
import com.microservice.microservice.Services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/email")
public class RestController {
    public static final String FIRST_TOKEN = "1ceb849e-53ea-40de-aa03-e9920e334f0-";
    public static final String SECOND_TOKEN = "4db5ac0d-afe9-4e81-8f50-6c64bf0a0fbc";

    //token = 1ceb849e-53ea-40de-aa03-e9920e334f0-4db5ac0d-afe9-4e81-8f50-6c64bf0a0fbc
    private final EmailService emailService;



    private boolean isTokenValid(String token){
        String validToken = FIRST_TOKEN + SECOND_TOKEN;
        String t = token.substring(7);
        return validToken.equals(t);
    }
    @PostMapping("/send")
    public ResponseEntity<MessageDto>send(@RequestHeader("Authorization") String token, @RequestBody NoteStudentDto noteStudentDto){
        MessageDto messageDto=new MessageDto();
        if(!isTokenValid(token)){

            messageDto.setFeedback("Invalid authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageDto);
        }
        emailService.sendEmail(noteStudentDto);
        messageDto.setFeedback("Email successfully sent!");
        return ResponseEntity.status(HttpStatus.OK).body(messageDto);
    }
}
