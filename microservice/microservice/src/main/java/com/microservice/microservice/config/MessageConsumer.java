package com.microservice.microservice.config;

import com.microservice.microservice.DTO.CalificativDto;
import com.microservice.microservice.DTO.NoteStudentDto;
import com.microservice.microservice.Services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageConsumer {


    private final EmailService emailService;

    @RabbitListener(queues = "emailQueue")
    public void consumeMessage(CalificativDto calificativDto){
        emailService.send(calificativDto);
    }


}
