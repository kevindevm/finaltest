package com.techlab.kevin.services;

import com.techlab.kevin.dto.OrderApiResponseDTO;
import com.techlab.kevin.entities.Order;
import com.techlab.kevin.entities.OrderItem;
import com.techlab.kevin.entities.Product;
import com.techlab.kevin.exceptions.NoStockException;
import com.techlab.kevin.exceptions.OrderNotFoundException;
import com.techlab.kevin.exceptions.ProductNotFoundException;
import com.techlab.kevin.repository.OrderRepository;
import com.techlab.kevin.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderApiResponseDTO addOrder(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        double total = 0.0;

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(item.getProduct().getId().toString()));

            if (product.getStock() < item.getQuantity()) {
                throw new NoStockException("Insufficient stock for product: " + product.getName());
            }

            product.updateStock(-item.getQuantity());
            item.setProduct(product);
            item.setOrder(order);

            total += product.getPrice() * item.getQuantity();
        }

        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);

        return new OrderApiResponseDTO("Order created successfully", saved);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderApiResponseDTO findById(Integer id) {
        Order found = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
        return new OrderApiResponseDTO("Order found", found);
    }

    public OrderApiResponseDTO updateOrder(Integer id, Order updatedData) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));

        if (updatedData.getStatus() != null) {
            existing.setStatus(updatedData.getStatus());
        }

        // Solo el estado es actualizable, no los ítems. Para editar ítems, creás un endpoint separado.
        Order saved = orderRepository.save(existing);
        return new OrderApiResponseDTO("Order updated successfully", saved);
    }

    public OrderApiResponseDTO deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }

        orderRepository.deleteById(id);
        return new OrderApiResponseDTO("Order deleted successfully", id);
    }
}
