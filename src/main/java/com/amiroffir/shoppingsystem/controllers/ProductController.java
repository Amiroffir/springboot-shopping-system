package com.amiroffir.shoppingsystem.controllers;

import com.amiroffir.shoppingsystem.enums.FilterTypes;
import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.Product;
import com.amiroffir.shoppingsystem.services.LogService;
import com.amiroffir.shoppingsystem.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private LogService logService;

    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> productsList = productService.getAllProducts();
            logService.logInfo("Products retrieved successfully");
            return ResponseEntity.ok(productsList);
        } catch (EmptyResultException e) {
            logService.logInfo("No products found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logService.logError("Error retrieving products- " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product addedProduct = productService.addProduct(product);
            logService.logInfo("Product " + product.getProductId() + " added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (EntityNotFoundException e) {
            logService.logError("No products found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logService.logError("Error adding product- " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(product);
            logService.logInfo("Product " + product.getProductId() + " updated successfully");
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            logService.logError("No products found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logService.logError("Error updating product- " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            logService.logInfo("Product " + id + " deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            logService.logError("product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logService.logError("Error deleting product- " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get products by price/name filter (or all products if no filter is specified)
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> getFilteredProducts(@RequestParam(required = false,defaultValue = "1000") Double maxPrice,
            @RequestParam(required = false,defaultValue = "0.0") Double minPrice,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false) String searchBy) {
        List<Product> filteredProducts;
        try {
            if (searchBy != null && searchBy.equalsIgnoreCase(FilterTypes.PRICE.toString())) {
                filteredProducts = productService.filterProductsByPrice(minPrice, maxPrice);
                logService.logInfo("Products filtered by price successfully");
            } else if (searchBy != null && searchBy.equalsIgnoreCase(FilterTypes.NAME.toString())) {
                filteredProducts = productService.filterProductsByName(name);
                logService.logInfo("Products filtered by name successfully");
            } else {
                // If searchBy is not specified or invalid, return all products
                filteredProducts = productService.getAllProducts();
                logService.logInfo("Products retrieved successfully");
            }
        } catch (EmptyResultException e) {
            logService.logInfo("No products found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logService.logError("Error retrieving products- " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(filteredProducts);
    }
}