package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.enums.FilterTypes;
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
        } catch (Exception e) {
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
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get products by price/name filter (or all products if no filter is specified)
    @GetMapping("/products/filter")
    public ResponseEntity<List<Product>> getFilteredProducts(@RequestParam(required = false,defaultValue = "1000") Double maxPrice,
            @RequestParam(required = false,defaultValue = "0.0") Double minPrice,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false) String searchBy) {
        List<Product> filteredProducts;
        try {
            if (searchBy != null && searchBy.equalsIgnoreCase(FilterTypes.PRICE.toString())) {
                filteredProducts = productService.filterProductsByPrice(minPrice, maxPrice);
            } else if (searchBy != null && searchBy.equalsIgnoreCase(FilterTypes.NAME.toString())) {
                filteredProducts = productService.filterProductsByName(name);
            } else {
                // If searchBy is not specified or invalid, return all products
                filteredProducts = productService.getAllProducts();
            }
        } catch (EmptyResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(filteredProducts);
    }
}