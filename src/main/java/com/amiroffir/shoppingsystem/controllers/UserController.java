package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.DTOs.UserResponseDTO;
import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.User;
import com.amiroffir.shoppingsystem.services.LogService;
import com.amiroffir.shoppingsystem.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody User user, HttpSession session) {
        try {
            UserResponseDTO currentUser = userService.login(user.getEmail(), user.getPassword(), session);
            logService.logInfo("User " + currentUser.getEmail() + " logged in");
            return ResponseEntity.ok(currentUser);
        }catch (EntityNotFoundException e){
            logService.logError("User " + user.getEmail() + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            logService.logError("Error logging in user " + user.getEmail() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody User user, HttpSession session) {
        try {
            UserResponseDTO newUser = userService.register(user, session);
            logService.logInfo("User " + newUser.getEmail() + " registered");
            return ResponseEntity.ok(newUser);
        } catch (Exception e){
            logService.logError("Error registering user " + user.getEmail() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        try {
            List<UserResponseDTO> usersList = userService.getAllUsers();
            logService.logInfo(usersList.size() + " users retrieved");
            return ResponseEntity.ok(usersList);
        } catch (EmptyResultException e) {
            logService.logError("No users found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logService.logError("Error retrieving users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(user);
            logService.logInfo("User " + updatedUser.getEmail() + " updated");
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            logService.logError("User " + user.getEmail() + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logService.logError("Error updating user " + user.getEmail() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}