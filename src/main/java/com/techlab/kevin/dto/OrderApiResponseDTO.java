package com.techlab.kevin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techlab.kevin.entities.Order;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderApiResponseDTO {
    private String message;
    private Integer id;
    private Order order;
    private List<Order> results;
    private Integer foundOrders;
    private Integer totalRecords;
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

    public OrderApiResponseDTO(String keyword, List<Order> results, Integer totalRecords) {
        this.message = "Found " + results.size() + " of " + totalRecords + " records for keyword: " + keyword;
        this.results = results;
        this.foundOrders = results.size();
        this.totalRecords = totalRecords;
        this.timestamp = LocalDateTime.now().toString();
    }
}
