package com.techlab.kevin.services;

import com.techlab.kevin.dto.OrderApiResponseDTO;
import com.techlab.kevin.dto.OrderUpdateDTO;
import com.techlab.kevin.entities.Order;
import com.techlab.kevin.exceptions.OrderNotFoundException;
import com.techlab.kevin.repository.OrderRepositoryJPA;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepositoryJPA orderRepository;

    public OrderService(OrderRepositoryJPA orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderApiResponseDTO createOrder(Order order) {
        Order saved = orderRepository.save(order);
        return new OrderApiResponseDTO("Order created successfully", saved.getId());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderApiResponseDTO getOrderById(Integer id) {
        Order found = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order ID " + id + " not found"));
        return new OrderApiResponseDTO("Order found", found);
    }

    public ResponseEntity<OrderApiResponseDTO> updateOrder(Integer id, OrderUpdateDTO dto) {
        return orderRepository.findById(id).map(existing -> {
            if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
            if (dto.getTotalAmount() != null && dto.getTotalAmount() > 0) {
                existing.setTotalAmount(dto.getTotalAmount());
            }
            Order updated = orderRepository.save(existing);
            return ResponseEntity.ok(new OrderApiResponseDTO("Order updated successfully", updated));
        }).orElseThrow(() -> new OrderNotFoundException("Order ID " + id + " not found"));
    }

    public ResponseEntity<OrderApiResponseDTO> deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order ID " + id + " not found");
        }
        orderRepository.deleteById(id);
        return ResponseEntity.ok(new OrderApiResponseDTO("Order deleted successfully", id));
    }
}
