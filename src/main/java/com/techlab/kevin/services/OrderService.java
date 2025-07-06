package com.techlab.kevin.services;

import com.techlab.kevin.dto.OrderApiResponseDTO;
import com.techlab.kevin.dto.OrderItemRequestDTO;
import com.techlab.kevin.dto.OrderRequestDTO;
import com.techlab.kevin.dto.OrderUpdateDTO;
import com.techlab.kevin.entities.Order;
import com.techlab.kevin.entities.OrderItem;
import com.techlab.kevin.entities.Product;
import com.techlab.kevin.exceptions.OrderNotFoundException;
import com.techlab.kevin.exceptions.ProductNotFoundException;
import com.techlab.kevin.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderApiResponseDTO createOrder(OrderRequestDTO dto) {
        Order order = new Order();
        order.setStatus(dto.getStatus());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemDTO.getProductId().toString()));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setSubtotal(product.getPrice() * itemDTO.getQuantity());
            item.setOrder(order);

            orderItems.add(item);
            total += item.getSubtotal();
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        return mapToResponseDTO(saved);
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
