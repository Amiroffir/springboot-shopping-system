package com.amiroffir.shoppingsystem.services;

import com.amiroffir.shoppingsystem.DTOs.OrderItemDTO;
import com.amiroffir.shoppingsystem.models.Order;
import com.amiroffir.shoppingsystem.models.OrderItem;
import com.amiroffir.shoppingsystem.repos.OrderItemRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private LogService logService;

    private static final String CART_SESSION_ATTRIBUTE = "cart";

    public List<OrderItemDTO> addToCart(OrderItemDTO orderItem, HttpSession session) {
        List<OrderItemDTO> cartItems = getOrCreateCart(session);
        cartItems.add(orderItem);
        return cartItems;
    }

    public List<OrderItemDTO> viewCart(HttpSession session) {
        return getOrCreateCart(session);
    }

    public OrderItemDTO updateCartItem(OrderItemDTO orderItemDTO, HttpSession session) {
        List<OrderItemDTO> cartItems = getOrCreateCart(session);
        OrderItemDTO updatedItem = null;
        for (OrderItemDTO item : cartItems) {
            if (item.getProduct().getProductId() == orderItemDTO.getProduct().getProductId()) {
                item.setQuantity(orderItemDTO.getQuantity());
                item.setItemAmount(item.getProduct().getPrice() * item.getQuantity());
                updatedItem = item;
            }
        }
        return updatedItem;
    }

    public void removeCartItem(OrderItemDTO orderItemDTO, HttpSession session) {
        List<OrderItemDTO> cartItems = getOrCreateCart(session);
        cartItems.removeIf(item -> item.getProduct().getProductId() == orderItemDTO.getProduct().getProductId());
    }

    private List<OrderItemDTO> getOrCreateCart(HttpSession session) {
            List<OrderItemDTO> cartItems = (List<OrderItemDTO>) session.getAttribute(CART_SESSION_ATTRIBUTE);
            logService.logInfo("Cart retrieved" + "session id: " + session.getId());
            if (cartItems == null) {
                cartItems = new ArrayList<>();
                session.setAttribute(CART_SESSION_ATTRIBUTE, cartItems);
                logService.logInfo("New cart created" + "session id: " + session.getId());
            }
        return cartItems;
    }

    public double getCartTotal(HttpSession session) {
        List<OrderItemDTO> cartItems = getOrCreateCart(session);
        double total = 0;
        for (OrderItemDTO item : cartItems) {
            total += item.getItemAmount();
        }
        logService.logInfo("Cart total: " + total + "session id: " + session.getId());
        return total;
    }

    public void addOrderItems(Order addedOrder, HttpSession session) {
        try {
            List<OrderItemDTO> cartItems = getOrCreateCart(session);
            List<OrderItem> orderItems = new ArrayList<>();
            for (OrderItemDTO item : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(addedOrder);
                orderItem.setProduct(item.getProduct());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setItemAmount(item.getItemAmount());
                orderItems.add(orderItem);
            }
            orderItemRepo.saveAll(orderItems);
            logService.logInfo("Order items added to DB" + "session id: " + session.getId());
            // Clear cart in session after adding order items to DB
            session.setAttribute(CART_SESSION_ATTRIBUTE, null);
            logService.logInfo("Cart cleared" + "session id: " + session.getId());
        } catch (Exception e) {
            logService.logError("Failed to add order items" + "session id: " + session.getId());
            System.out.println("Failed to add order items.");
        }
    }
}