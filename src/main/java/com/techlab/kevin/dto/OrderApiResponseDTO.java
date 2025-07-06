package com.techlab.kevin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techlab.kevin.entities.Order;
import com.techlab.kevin.entities.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderApiResponseDTO {
    private String message;
    private Integer id;
    private Order order;
    private String timestamp;

    public OrderApiResponseDTO(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public OrderApiResponseDTO(String message, Integer id) {
        this.message = message;
        this.id = id;
        this.timestamp = LocalDateTime.now().toString();
    }

    public OrderApiResponseDTO(String message, Order order) {
        this.message = message;
        this.order = order;
        this.timestamp = LocalDateTime.now().toString();
    }

    public OrderApiResponseDTO(String orderUpdatedSuccessfully, Integer id, String status, List<OrderItem> items, Double totalAmount) {
        this.timestamp = LocalDateTime.now().toString();

    }
}
