package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.Order;
import com.amiroffir.shoppingsystem.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/get-by-user/{userId}")
    public ResponseEntity<List<Order>> getOrdersHistory(@PathVariable int userId) {
        try {
             List<Order> ordersList = orderService.getOrdersHistoryByUser(userId);
            return ResponseEntity.ok(ordersList);
        } catch (EmptyResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}