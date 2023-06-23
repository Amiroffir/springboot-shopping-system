package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.DTOs.OrderItemDTO;
import com.amiroffir.shoppingsystem.services.OrderItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/order-items/add")
    public ResponseEntity<List<OrderItemDTO>> addToCart(@RequestBody OrderItemDTO orderItemDTO, HttpSession session) {
        try {
            List<OrderItemDTO> cartItems = orderItemService.addToCart(orderItemDTO, session);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/order-items/view")
    public ResponseEntity<List<OrderItemDTO>> viewCart(HttpSession session) {
        try {
            List<OrderItemDTO> cartItems = orderItemService.viewCart(session);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/order-items/update")
    public ResponseEntity<OrderItemDTO> updateCartItem(@RequestBody OrderItemDTO orderItemDTO, HttpSession session) {
        try {
            OrderItemDTO updatedItem = orderItemService.updateCartItem(orderItemDTO, session);
            if (updatedItem != null) {
                return ResponseEntity.ok(updatedItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/order-items/remove")
    public ResponseEntity<Void> removeCartItem(@RequestBody OrderItemDTO orderItemDTO, HttpSession session) {
        try {
            orderItemService.removeCartItem(orderItemDTO, session);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}