package com.techlab.kevin.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ProductCreateDTO {
    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @Min(0)
    private Integer stock;
}