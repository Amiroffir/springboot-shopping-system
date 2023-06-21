package com.amiroffir.shoppingsystem.services;

import com.amiroffir.shoppingsystem.models.Product;
import com.amiroffir.shoppingsystem.models.User;
import com.amiroffir.shoppingsystem.repos.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User updateUser(User user) {
        try {
            if (!userRepo.existsById(user.getUserId())) {
                throw new EntityNotFoundException();
            }
            return userRepo.save(user);
        } catch (EntityNotFoundException e) {
            throw e; // Rethrow the EntityNotFoundException
        } catch (Exception e) {
            // Log or handle other exceptions
            throw e;
        }
    }
}