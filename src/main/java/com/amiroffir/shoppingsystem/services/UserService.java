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
    @Autowired
    private LogService logService;

    private static final String USER_SESSION_ATTRIBUTE = "user";

    public UserResponseDTO register(User user, HttpSession session) {
        try {
            String userEmail = user.getEmail();
            if (userRepo.existsByEmail(userEmail)) {
                logService.logError("User already exists");
                throw new RuntimeException("User already exists");
            }
            if (userEmail.contains(UserTypes.Admin.getValue())) {
                user.setUserType(UserTypes.Admin.getValue());
                logService.logInfo("New admin registered" + user.getName());
            } else {
                user.setUserType(UserTypes.Customer.getValue());
                logService.logInfo("New customer registered" + user.getName());
            }
            User newUser = userRepo.save(user);
            // Set the user in the session
            session.setAttribute(USER_SESSION_ATTRIBUTE, newUser);
            logService.logInfo("User registered successfully and set in session");
            return new UserResponseDTO(newUser.getName(), newUser.getEmail(), newUser.getUserType());
        } catch (Exception e) {
            logService.logError("Error registering user");
            throw e;
        }
    }

    public UserResponseDTO login(String email, String password, HttpSession session) {
        try {
            User user = userRepo.findByEmailAndPassword(email, password);
            if (user != null) {
                // Set the user in the session
                session.setAttribute(USER_SESSION_ATTRIBUTE, user);
                logService.logInfo("User logged in successfully and set in session");
                return new UserResponseDTO(user.getName(), user.getEmail(), user.getUserType());
            } else {
                logService.logError("Invalid email or password");
                throw new RuntimeException("Invalid email or password");
            }
        } catch (EntityNotFoundException e) {
            logService.logError("Invalid email or password");
            throw e;
        } catch (Exception e) {
            logService.logError("Error logging in user");
            throw e;
        }
    }

    public User getCurrentUser(HttpSession session) {
        // Get the user from the session
        logService.logInfo("Getting current user from session");
        return (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
    }

    public User updateUser(User user) {
        try {
            if (!userRepo.existsById(user.getUserId())) {
                logService.logError("User not found to update");
                throw new EntityNotFoundException();
            }
            User userToUpdate = userRepo.findById(user.getUserId()).orElseThrow();
            userToUpdate.setName(user.getName());
            userToUpdate.setPassword(user.getPassword());
            return userRepo.save(userToUpdate);
        } catch (Exception e) {
            logService.logError("Error updating user" + user.getUserId());
            throw e;
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        try {
            List<User> usersList = userRepo.findAll();
            if (usersList.isEmpty()) {
                logService.logInfo("No users found");
                throw new EmptyResultException();
            }
            List<UserResponseDTO> usersResponseList = new ArrayList<>();
            for (User user : usersList) {
                usersResponseList.add(new UserResponseDTO(user.getName(), user.getEmail(), user.getUserType()));
            }
            return usersResponseList;
        } catch (Exception e) {
            logService.logError("Error getting all users");
            throw e;
        }
    }
}