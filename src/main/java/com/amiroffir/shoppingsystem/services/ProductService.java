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
    @Autowired
    private LogService logService;

    public List<Product> getAllProducts() {
        try {
            List<Product> productsList = productRepo.findAll();
            if (productsList.isEmpty()) {
                logService.logInfo("No products found");
                throw new EmptyResultException();
            }
            return productsList;
        } catch (Exception e) {
            logService.logError("Unable to retrieve products " + e.getMessage());
            throw e;
        }
    }

    public Product addProduct(Product product) {
        try {
            Product addedProduct = productRepo.save(product);
            return addedProduct;
        } catch (EntityNotFoundException e) {
            logService.logError("Unable to add product " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logService.logError("Unable to add product " + e.getMessage());
            throw e;
        }
    }

    public Product updateProduct(Product product) {
        try {
            if(!productRepo.existsById(product.getProductId())){
                logService.logError("Unable to update product, product not found");
                throw new EntityNotFoundException();
            }
            return productRepo.save(product);
        } catch (Exception e) {
            logService.logError("Unable to update product " + e.getMessage());
            throw e;
        }
    }

    public void deleteProduct(int product) {
        try {
            if (!productRepo.existsById(product)) {
                logService.logError("Unable to delete product, product not found");
                throw new EntityNotFoundException();
            }
            productRepo.deleteById(product);
        } catch (Exception e) {
            logService.logError("Unable to delete product " + e.getMessage());
            throw e;
        }
    }

    public List<Product> filterProductsByPrice(Double minPrice, Double maxPrice) {
        try {
            List<Product> productsList = productRepo.findAllByPriceBetween(minPrice, maxPrice);
            if (productsList.isEmpty()) {
                logService.logInfo("No products found in price range " + minPrice + " - " + maxPrice);
                throw new EmptyResultException();
            }
            return productsList;
        } catch (Exception e) {
            logService.logError("Unable to retrieve products " + e.getMessage());
            throw e;
        }
    }

    public List<Product> filterProductsByName(String name) {
        try {
            List<Product> productsList = productRepo.findAllByNameContaining(name);
            if (productsList.isEmpty()) {
                logService.logInfo("No products found with name " + name);
                throw new EmptyResultException();
            }
            return productsList;
        } catch (Exception e) {
            logService.logError("Unable to retrieve products " + e.getMessage());
            throw e;
        }
    }
}