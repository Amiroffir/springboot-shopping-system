package com.amiroffir.shoppingsystem.services;

import com.amiroffir.shoppingsystem.exceptions.EmptyResultException;
import com.amiroffir.shoppingsystem.models.Order;
import com.amiroffir.shoppingsystem.repos.OrderRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
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
            // Log
            throw e;
        }
    }

    public Order addOrder(HttpSession session) {
        try {
            Order orderToAdd = createNewOrder(session);
            Order addedOrder = orderRepo.save(orderToAdd);
            orderItemService.addOrderItems(addedOrder, session);
            return addedOrder;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Log
            throw e;
        }
    }

    private Order createNewOrder(HttpSession session) {
        Order orderToAdd = new Order();
        orderToAdd.setUser(userService.getCurrentUser(session));
        orderToAdd.setOrderDate(new Date());
        orderToAdd.setTotalAmount(orderItemService.getCartTotal(session));
        return orderToAdd;
    }
}