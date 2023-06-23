package com.amiroffir.shoppingsystem.services;

import com.amiroffir.shoppingsystem.DTOs.UserResponseDTO;
import com.amiroffir.shoppingsystem.enums.UserTypes;
import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.User;
import com.amiroffir.shoppingsystem.repos.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    private static final String USER_SESSION_ATTRIBUTE = "user";

    public UserResponseDTO register(User user, HttpSession session) {
        try {
            String userEmail = user.getEmail();
            if (userRepo.existsByEmail(userEmail)) {
                throw new RuntimeException("User already exists");
            }
            if (userEmail.contains(UserTypes.Admin.getValue())) {
                user.setUserType(UserTypes.Admin.getValue());
            } else {
                user.setUserType(UserTypes.Customer.getValue());
            }
            User newUser = userRepo.save(user);
            // Set the user in the session
            session.setAttribute(USER_SESSION_ATTRIBUTE, newUser);
            return new UserResponseDTO(newUser.getName(), newUser.getEmail(), newUser.getUserType());
        } catch (Exception e) {
            // Log
            throw e;
        }
    }

    public UserResponseDTO login(String email, String password, HttpSession session) {
        try {
            User user = userRepo.findByEmailAndPassword(email, password);
            if (user != null) {
                // Set the user in the session
                session.setAttribute(USER_SESSION_ATTRIBUTE, user);
                return new UserResponseDTO(user.getName(), user.getEmail(), user.getUserType());
            } else {
                // Handle invalid login
                throw new RuntimeException("Invalid email or password");
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Log
            throw e;
        }
    }

    public User getCurrentUser(HttpSession session) {
        // Get the user from the session
        return (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
    }

    public User updateUser(User user) {
        try {
            if (!userRepo.existsById(user.getUserId())) {
                throw new EntityNotFoundException();
            }
            User userToUpdate = userRepo.findById(user.getUserId()).orElseThrow();
            userToUpdate.setName(user.getName());
            userToUpdate.setPassword(user.getPassword());
            return userRepo.save(userToUpdate);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Log
            throw e;
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        try {
            List<User> usersList = userRepo.findAll();
            if (usersList.isEmpty()) {
                throw new EmptyResultException();
            }
            List<UserResponseDTO> usersResponseList = new ArrayList<>();
            for (User user : usersList) {
                usersResponseList.add(new UserResponseDTO(user.getName(), user.getEmail(), user.getUserType()));
            }
            return usersResponseList;
        } catch (EmptyResultException e) {
            throw e;
        } catch (Exception e) {
            // Log
            throw e;
        }
    }
}