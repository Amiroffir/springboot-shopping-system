package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.Order;
import com.amiroffir.shoppingsystem.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            System.out.println(ordersList.size() + " orders retrieved");
            return ResponseEntity.ok(ordersList);
        } catch (EmptyResultException e) {
            System.out.println("No orders found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            System.out.println("Error retrieving orders- " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
  @PostMapping("/orders/checkout")
    public ResponseEntity<Order> addOrder(HttpSession session) {
        try {
            Order addedOrder = orderService.addOrder(session);
            return ResponseEntity.ok(addedOrder);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}