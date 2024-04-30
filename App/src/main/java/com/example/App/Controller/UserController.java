package com.example.App.Controller;
import com.example.App.Model.UserEntity;
import com.example.App.Model.UserEntityDTO;
import com.example.App.exception.UserNotFoundException;
import com.example.App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserEntity> userEntities = userService.getAllUsers();
            return ResponseEntity.ok(userEntities);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
        }
    }

    @GetMapping("/{userID}")
    public ResponseEntity<?> getUser(@RequestParam("userID") long userID) {
        try {
            UserEntity userEntity = userService.getUser(userID);
            UserEntityDTO userResponseDTO = new UserEntityDTO();
            userResponseDTO.setEmail(userEntity.getEmail());
            userResponseDTO.setName(userEntity.getName());
            userResponseDTO.setPassword(userEntity.getPassword());
            return ResponseEntity.ok(userResponseDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
    }
}
