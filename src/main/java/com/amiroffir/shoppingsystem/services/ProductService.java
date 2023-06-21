package com.amiroffir.shoppingsystem.services;

import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.Product;
import com.amiroffir.shoppingsystem.repos.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        try {
            List<Product> productsList = productRepo.findAll();
            if (productsList.isEmpty()) {
                throw new EmptyResultException();
            }
            return productsList;
        } catch (Exception e) {
            // Log or handle other exceptions
            throw e;
        }
    }

    public Product addProduct(Product product) {
        try {
            Product addedProduct = productRepo.save(product);
            return addedProduct;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Log
            throw e;
        }
    }

    public Product updateProduct(Product product) {
        try {
            if(!productRepo.existsById(product.getProductId())){
                throw new EntityNotFoundException();
            }
            return productRepo.save(product);
        } catch (EntityNotFoundException e) {
            throw e; // Rethrow the EntityNotFoundException
        } catch (Exception e) {
            // Log or handle other exceptions
            throw e;
        }
    }

    public void deleteProduct(int product) {
        try {
            if (!productRepo.existsById(product)) {
                throw new EntityNotFoundException();
            }
            productRepo.deleteById(product);
        } catch (Exception e) {
            // Log
            throw e;
        }
    }

    public List<Product> filterProductsByPrice(Double minPrice, Double maxPrice) {
        try {
            List<Product> productsList = productRepo.findAllByPriceBetween(minPrice, maxPrice);
            if (productsList.isEmpty()) {
                throw new EmptyResultException();
            }
            return productsList;
        } catch (Exception e) {
            // Log or handle other exceptions
            throw e;
        }
    }

    public List<Product> filterProductsByName(String name) {
        try {
            List<Product> productsList = productRepo.findAllByNameContaining(name);
            if (productsList.isEmpty()) {
                throw new EmptyResultException();
            }
            return productsList;
        } catch (Exception e) {
            // Log or handle other exceptions
            throw e;
        }
    }
}