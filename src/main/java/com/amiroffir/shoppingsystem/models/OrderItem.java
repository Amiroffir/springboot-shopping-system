package com.amiroffir.shoppingsystem.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orderitems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  @Column(name = "order_item_id")
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

 //   @Column(name = "quantity")
    private int quantity;

  //  @Column(name = "item_amount")
    private double itemAmount;
}