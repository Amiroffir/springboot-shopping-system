package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.DTOs.UserResponseDTO;
import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.User;
import com.amiroffir.shoppingsystem.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody User user, HttpSession session) {
        try {
            UserResponseDTO currentUser = userService.login(user.getEmail(), user.getPassword(), session);
            return ResponseEntity.ok(currentUser);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody User user, HttpSession session) {
        try {
            UserResponseDTO newUser = userService.register(user, session);
            return ResponseEntity.ok(newUser);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        try {
            List<UserResponseDTO> usersList = userService.getAllUsers();
            return ResponseEntity.ok(usersList);
        } catch (EmptyResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/users/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}