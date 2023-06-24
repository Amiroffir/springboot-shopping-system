package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.Order;
import com.amiroffir.shoppingsystem.services.LogService;
import com.amiroffir.shoppingsystem.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/orders")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private LogService logService;

    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<List<Order>> getOrdersHistory(@PathVariable int userId) {
        try {
             List<Order> ordersList = orderService.getOrdersHistoryByUser(userId);
             logService.logInfo("Retrieved " + ordersList.size() + " orders for user " + userId);
            return ResponseEntity.ok(ordersList);
        } catch (EmptyResultException e) {
            logService.logInfo("No orders found for user " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            logService.logError("Error retrieving orders for user " + userId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
  @PostMapping("/checkout")
    public ResponseEntity<Order> addOrder(HttpSession session) {
        try {
            Order addedOrder = orderService.addOrder(session);
            logService.logInfo("Added order " + addedOrder.getOrderId() + " for user " + addedOrder.getUser().getUserId());
            return ResponseEntity.ok(addedOrder);
        } catch (EntityNotFoundException e) {
            logService.logError("Error adding order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logService.logError("Error adding order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}