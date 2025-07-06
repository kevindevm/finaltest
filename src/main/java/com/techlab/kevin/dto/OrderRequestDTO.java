package com.techlab.kevin.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private String status;
    private List<OrderItemRequestDTO> items;
}
