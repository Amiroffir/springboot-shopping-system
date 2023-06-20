package com.amiroffir.shoppingsystem.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "product_id")
    private int productId;

 //   @Column(name = "name")
    private String name;

  //  @Column(name = "description")
    private String description;

  //  @Column(name = "price")
    private double price;

  //  @Column(name = "quantity")
    private int quantity;
}