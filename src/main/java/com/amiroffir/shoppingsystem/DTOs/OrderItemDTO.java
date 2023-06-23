package com.amiroffir.shoppingsystem.DTOs;

import com.amiroffir.shoppingsystem.models.Order;
import com.amiroffir.shoppingsystem.models.Product;
import lombok.Data;

@Data
public class OrderItemDTO {

    private Product product;

    private int quantity;

    private double itemAmount;
}