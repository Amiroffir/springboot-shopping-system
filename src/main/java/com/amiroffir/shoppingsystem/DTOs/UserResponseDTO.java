package com.amiroffir.shoppingsystem.DTOs;

public class UserResponseDTO {


    private String name;

    private String email;

    private String userType;

    public UserResponseDTO(String name, String email, String userType) {
        this.name = name;
        this.email = email;
        this.userType = userType;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}