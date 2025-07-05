package com.techlab.kevin.dto;

import lombok.Data;

@Data
public class OrderUpdateDTO {
    private Double totalAmount;
    private String status;
}
