package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.DTOs.OrderItemDTO;
import com.amiroffir.shoppingsystem.models.OrderItem;
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
    public ResponseEntity<String> addToCart(@RequestBody OrderItemDTO requestDTO, HttpSession session) {
        try {
            orderItemService.addToCart(requestDTO, session);
            return ResponseEntity.ok("Item added to cart.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item to cart.");
        }
    }

    @GetMapping("/order-items/view")
    public ResponseEntity<List<OrderItem>> viewCart(HttpSession session) {
        List<OrderItem> cartItems = orderItemService.viewCart(session);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/order-items/update")
    public ResponseEntity<String> updateCartItem(@RequestBody OrderItemDTO requestDTO, HttpSession session) {
        try {
            orderItemService.updateCartItem(requestDTO, session);
            return ResponseEntity.ok("Cart item updated.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cart item.");
        }
    }

    @DeleteMapping("/order-items/remove")
    public ResponseEntity<String> removeCartItem(@RequestBody OrderItemDTO requestDTO, HttpSession session) {
        try {
            orderItemService.removeCartItem(requestDTO, session);
            return ResponseEntity.ok("Cart item removed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove cart item.");
        }
    }
}