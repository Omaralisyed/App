package com.example.App.Controller;

import com.example.App.exception.IncorrectCredentialsException;
import com.example.App.exception.UserAlreadyExistsException;
import com.example.App.exception.UserNotFoundException;
import com.example.App.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest request) {
        try {
            userService.signUp(request.getEmail(), request.getName(), request.getPassword());
            return ResponseEntity.ok("Account Creation Successful");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden, Account already exists");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        try {
            boolean success = userService.login(request.getEmail(), request.getPassword());
            if (success) {
                return ResponseEntity.ok("Login Successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username/Password Incorrect");
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        } catch (IncorrectCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
        }
    }

    @Getter
    @Setter
    public static class UserLoginRequest {
        private String email;
        private String password;
    }


    // Request body for sign up
    @Setter
    @Getter
    public static class UserSignUpRequest {
        private String email;
        private String name;
        private String password;
    }
}
