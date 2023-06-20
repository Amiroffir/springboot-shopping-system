package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.Product;
import com.amiroffir.shoppingsystem.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
             List<Product> productsList = productService.getAllProducts();
                return ResponseEntity.ok(productsList);
        } catch (EmptyResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/products/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product addedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/products/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}