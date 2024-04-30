package com.example.App.service;


import com.example.App.Model.UserEntity;
import com.example.App.exception.IncorrectCredentialsException;
import com.example.App.exception.UserAlreadyExistsException;
import com.example.App.exception.UserNotFoundException;
import com.example.App.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserEntity getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return userEntities;
    }

    public void signUp(String email, String name, String password) throws UserAlreadyExistsException {
        // Check if user with provided email already exists
        if (userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        // Create new user
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setEmail(email);
        newUserEntity.setName(name);
        newUserEntity.setPassword(password);

        userRepository.save(newUserEntity);
    }



    public boolean login(String email, String password) throws UserNotFoundException, IncorrectCredentialsException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        if (!userEntity.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("Incorrect password for user with email " + email);
        }
        return true;
    }


}

