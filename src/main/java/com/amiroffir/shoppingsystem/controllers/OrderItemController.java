package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.DTOs.OrderItemDTO;
import com.amiroffir.shoppingsystem.services.LogService;
import com.amiroffir.shoppingsystem.services.OrderItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/order-items")
@RestController
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private LogService logService;

    @PostMapping("/add")
    public ResponseEntity<List<OrderItemDTO>> addToCart(@RequestBody OrderItemDTO orderItemDTO, HttpSession session) {
        try {
            List<OrderItemDTO> cartItems = orderItemService.addToCart(orderItemDTO, session);
            logService.logInfo("Added " + orderItemDTO.getQuantity() + " " + orderItemDTO.getProduct().getName() + " to cart");
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            logService.logError("Error adding " + orderItemDTO.getQuantity() + " " + orderItemDTO.getProduct().getName() + " to cart");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view")
    public ResponseEntity<List<OrderItemDTO>> viewCart(HttpSession session) {
        try {
            List<OrderItemDTO> cartItems = orderItemService.viewCart(session);
            logService.logInfo("Cart retrieved successfully from session");
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            logService.logError("Error retrieving cart from session");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<OrderItemDTO> updateCartItem(@RequestBody OrderItemDTO orderItemDTO, HttpSession session) {
        try {
            OrderItemDTO updatedItem = orderItemService.updateCartItem(orderItemDTO, session);
            if (updatedItem != null) {
                logService.logInfo("Updated " + orderItemDTO.getQuantity() + " " + orderItemDTO.getProduct().getName() + " in cart");
                return ResponseEntity.ok(updatedItem);
            } else {
                logService.logError("Error updating " + orderItemDTO.getQuantity() + " " + orderItemDTO.getProduct().getName() + " in cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logService.logError("Error updating " + orderItemDTO.getQuantity() + " " + orderItemDTO.getProduct().getName() + " in cart");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeCartItem(@RequestBody OrderItemDTO orderItemDTO, HttpSession session) {
        try {
            orderItemService.removeCartItem(orderItemDTO, session);
            logService.logInfo("Removed " + orderItemDTO.getQuantity() + " " + orderItemDTO.getProduct().getName() + " from cart");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logService.logError("Error removing " + orderItemDTO.getQuantity() + " " + orderItemDTO.getProduct().getName() + " from cart");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}