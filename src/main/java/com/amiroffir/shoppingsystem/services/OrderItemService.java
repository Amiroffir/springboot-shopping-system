package com.amiroffir.shoppingsystem.services;

import com.amiroffir.shoppingsystem.DTOs.OrderItemDTO;
import com.amiroffir.shoppingsystem.models.OrderItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {
        private static final String CART_SESSION_ATTRIBUTE = "cart";

        public void addToCart(OrderItemDTO requestDTO, HttpSession session) {
            OrderItem orderItem = createOrderItemFromRequest(requestDTO);
            List<OrderItem> cartItems = getOrCreateCart(session);
            cartItems.add(orderItem);
        }

        public List<OrderItem> viewCart(HttpSession session) {
            return getOrCreateCart(session);
        }

        public void updateCartItem(OrderItemDTO requestDTO, HttpSession session) {
            List<OrderItem> cartItems = getOrCreateCart(session);
            for (OrderItem item : cartItems) {
                if (item.getOrderItemId() == requestDTO.getOrderItemId()) {
                    item.setQuantity(requestDTO.getQuantity());
                    item.setItemAmount(item.getProduct().getPrice() * item.getQuantity());
                    break;
                }
            }
        }

        public void removeCartItem(OrderItemDTO requestDTO, HttpSession session) {
            List<OrderItem> cartItems = getOrCreateCart(session);
            cartItems.removeIf(item -> item.getOrderItemId() == requestDTO.getOrderItemId());
        }

        private List<OrderItem> getOrCreateCart(HttpSession session) {
            List<OrderItem> cartItems = (List<OrderItem>) session.getAttribute(CART_SESSION_ATTRIBUTE);
            if (cartItems == null) {
                cartItems = new ArrayList<>();
                session.setAttribute(CART_SESSION_ATTRIBUTE, cartItems);
            }
            return cartItems;
        }

        private OrderItem createOrderItemFromRequest(OrderItemDTO requestDTO) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(requestDTO.getOrderItemId());
            orderItem.setOrder(requestDTO.getOrder());
            orderItem.setProduct(requestDTO.getProduct());
            orderItem.setQuantity(requestDTO.getQuantity());
            orderItem.setItemAmount(requestDTO.getItemAmount());
            return orderItem;
        }
    }