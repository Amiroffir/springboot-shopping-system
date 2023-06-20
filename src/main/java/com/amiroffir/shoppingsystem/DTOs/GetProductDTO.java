package com.amiroffir.shoppingsystem.DTOs;

import lombok.Data;

@Data
public class GetProductDTO {

    private String name;

    private String description;

    private double price;

    private int quantity;
}