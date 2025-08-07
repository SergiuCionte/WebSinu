package com.microservice.microservice.Services;

import com.microservice.microservice.DTO.CalificativDto;
import com.microservice.microservice.DTO.NoteStudentDto;



public interface EmailService {

    public void sendEmail(NoteStudentDto noteStudentDto);

    public void send(CalificativDto calificativDto);
}
