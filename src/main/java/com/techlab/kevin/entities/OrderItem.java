package com.techlab.kevin.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class OrderItem {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Product product, Integer quantity) {
    }
    public OrderItem() {
    }
}