package com.amiroffir.shoppingsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Create all the getters, setters, equals and toString methods, based on the fields
@Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private int userId;

        private String name;

        private String email;

        private String userType;

        private String password;
    }