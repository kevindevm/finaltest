package com.techlab.kevin.dto;

import lombok.Data;

@Data
public class OrderItemRequestDTO {
    private Integer productId;
    private Integer quantity;
}
