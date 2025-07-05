package com.techlab.kevin.controller;

import com.techlab.kevin.dto.OrderApiResponseDTO;
import com.techlab.kevin.dto.OrderUpdateDTO;
import com.techlab.kevin.entities.Order;
import com.techlab.kevin.services.OrderServiceold;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceold service;

    public OrderController(OrderServiceold service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderApiResponseDTO> create(@Valid @RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(order));
    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderApiResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderApiResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody OrderUpdateDTO dto) {
        return service.updateOrder(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderApiResponseDTO> delete(@PathVariable Integer id) {
        return service.deleteOrder(id);
    }
}
