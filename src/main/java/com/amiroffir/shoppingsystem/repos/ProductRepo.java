package com.amiroffir.shoppingsystem.repos;

import com.amiroffir.shoppingsystem.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findAllByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findAllByNameContaining(String name);
}