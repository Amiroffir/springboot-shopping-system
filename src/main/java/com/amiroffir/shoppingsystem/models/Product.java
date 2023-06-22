package com.amiroffir.shoppingsystem.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int productId;

    private String name;

    private String description;

    private double price;

    private int quantity;
}