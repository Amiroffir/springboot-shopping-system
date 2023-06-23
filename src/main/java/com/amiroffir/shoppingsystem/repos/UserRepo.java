package com.amiroffir.shoppingsystem.repos;

import com.amiroffir.shoppingsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String username, String password);

    boolean existsByEmail(String email);
}