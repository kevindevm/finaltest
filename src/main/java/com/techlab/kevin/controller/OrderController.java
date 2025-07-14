package com.techlab.kevin.controller;

import com.techlab.kevin.dto.OrderApiResponseDTO;
import com.techlab.kevin.entities.Order;
import com.techlab.kevin.entities.OrderItem;
import com.techlab.kevin.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }
//  @Operation(summary = "Crea Una Orden")
//  @ApiResponse(responseCode = "201")
  @PostMapping
  public ResponseEntity<OrderApiResponseDTO> create(@Valid @RequestBody Order order) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.addOrder(order));
  }

  @GetMapping
  public List<Order> getAll() {
    return service.getAllOrders();
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderApiResponseDTO> getById(@PathVariable Integer id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderApiResponseDTO> update(@PathVariable Integer id,
      @Valid @RequestBody Order order) {
    return ResponseEntity.ok(service.updateOrder(id, order));
  }

  @PatchMapping("/{orderId}/items/{productId}")
  public ResponseEntity<?> updateItemQuantity(
      @PathVariable Integer orderId,
      @PathVariable Integer productId,
      @RequestParam Integer quantity) {

    OrderItem updatedItem = service.updateItemQuantity(orderId, productId, quantity);

    return ResponseEntity.ok(
        new OrderApiResponseDTO("Order item updated successfully", orderId, productId,
            updatedItem.getQuantity(), updatedItem.getSubtotal())
    );
  }

  @DeleteMapping("/{orderId}/product/{productId}")
  public ResponseEntity<?> removeProductFromOrder(
      @PathVariable Integer orderId,
      @PathVariable Integer productId) {

    Order updated = service.removeItemByProduct(orderId, productId);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<OrderApiResponseDTO> delete(@PathVariable Integer id) {
    return ResponseEntity.ok(service.deleteOrder(id));
  }

}
