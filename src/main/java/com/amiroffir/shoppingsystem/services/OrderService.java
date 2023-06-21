package com.amiroffir.shoppingsystem.services;

import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.Order;
import com.amiroffir.shoppingsystem.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    public List<Order> getOrdersHistoryByUser(int userId) {
        try {
            List<Order> ordersList = orderRepo.findAllByUserUserId(userId);
            if (ordersList.isEmpty()) {
                throw new EmptyResultException();
            }
            return ordersList;
        } catch (Exception e) {
            // Log or handle other exceptions
            throw e;
        }
    }
}