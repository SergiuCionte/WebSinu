package com.PSapplication.PSapplication.Validators;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component

public class UserValidatorImpl implements UserValidator {

    // Regular expressions for validation
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";
    private static final String EMAIL_REGEX = "\\S+@\\S+\\.\\S+";
    private static final String ROLE_REGEX = "(PROFESOR|ADMIN|STUDENT)";

    public boolean validateUsername(String username) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateRole(String role) {
        Pattern pattern = Pattern.compile(ROLE_REGEX);
        Matcher matcher = pattern.matcher(role);
        return matcher.matches();
    }

    // You can add more validation methods for other fields as needed


}