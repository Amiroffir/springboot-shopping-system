package com.amiroffir.shoppingsystem.repos;

import com.amiroffir.shoppingsystem.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
   List<Order> findAllByUserUserId(int userId);
}