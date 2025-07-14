package com.techlab.kevin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techlab.kevin.entities.Order;
import com.techlab.kevin.entities.OrderItem;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderApiResponseDTO {

  private String message;
  private Integer id;
  private Integer productId;
  private Integer quantity;
  private Double subtotal;
  private Double newSubtotal;
  private Order order;
  private String timestamp;
  private String status;
  private List<OrderItem> items;
  private Double totalAmount;

  public OrderApiResponseDTO(String msg) {
    this.message = msg;
    this.timestamp = LocalDateTime.now().toString();
  }

  public OrderApiResponseDTO(String msg, Integer id) {
    this.message = msg;
    this.id = id;
    this.timestamp = LocalDateTime.now().toString();
  }

  public OrderApiResponseDTO(String msg, Order order) {
    this.message = msg;
    this.order = order;
    this.timestamp = LocalDateTime.now().toString();
  }



  public OrderApiResponseDTO(String msg, Integer orderId
      , Integer productId,
      @Min(value = 1, message = "Quantity must be at least 1") Integer quantity, double subtotal) {
    this.message = msg;
    this.productId = productId;
    this.newSubtotal = subtotal;
    this.id = orderId;
    this.quantity = quantity;
    this.subtotal = subtotal;
    this.timestamp = LocalDateTime.now().toString();

  }

  public OrderApiResponseDTO(String msg, Integer id, String status, List<OrderItem> items,
      Double totalAmount) {
    this.message = msg;
    this.id = id;
    this.status = status;
    this.items = items;
    this.totalAmount = totalAmount;

    this.timestamp = LocalDateTime.now().toString();
  }
}
