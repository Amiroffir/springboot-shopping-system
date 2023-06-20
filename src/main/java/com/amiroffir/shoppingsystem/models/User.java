package com.amiroffir.shoppingsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Create all the getters, setters, equals, hash, and toString methods, based on the fields
    // JPA annotations to make the object ready for storage in a JPA-based database
@Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
      //  @Column(name = "user_id")
        private int userId;

    //    @Column(name = "name")
        private String name;

     //   @Column(name = "email")
        private String email;

     //   @Column(name = "UserType")
        private String userType;

     //   @Column(name = "password")
        private String password;
    }