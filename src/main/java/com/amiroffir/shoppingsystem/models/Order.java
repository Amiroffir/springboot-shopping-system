package com.amiroffir.shoppingsystem.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

 //   @Column(name = "order_date")
    private Date orderDate;

   // @Column(name = "total_amount")
    private double totalAmount;
}