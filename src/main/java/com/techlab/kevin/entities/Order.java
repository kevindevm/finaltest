package com.techlab.kevin.entities;

import com.techlab.kevin.exceptions.NoStockException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;


     @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public Order(Product product, Integer quantity) {
        this();
        this.cart = new ArrayList<>();
        try {
            product.updateStock(-quantity);
        } catch (NoStockException e) {
            throw new NoStockException("No hay stock suficiente para el producto con ID: " + product.getId());
        }


        this.cart.add(new OrderItem(product, quantity));

    }

    public Order() {

    }

    public void addItem(Product product, Integer quantity) {
        for (OrderItem item : this.cart) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Si no estaba, lo agregamos como nuevo
        this.cart.add(new OrderItem(product, quantity));
    }


    public void removeItem() {
    }




    public String removeItemByID(Integer ID) {
        boolean removed = this.cart.removeIf(item -> item.getProduct().getId() == ID);
        return removed ? "✔ Eliminado correctamente" : "✖ Error: Ningún producto con ese ID";
    }


    public String removeItemByName(String name) {
        boolean removed = this.cart.removeIf(item -> item.getProduct().getName().equalsIgnoreCase(name));
        return removed ? "✔ Eliminado correctamente" : "✖ Error: Ningún producto con ese nombre";
    }


    public double getTotal() {
        double total = 0;
        for (OrderItem item : this.cart) {
            total += item.getSubtotal();
        }
        return total;
    }





    public static void showCart(Integer orderId, List<OrderItem> cart) {
        double total = 0;

        // Encabezados
        String[] headers = {"Cant", "Producto", "P/U", "Subtotal"};

        // Calcular anchos máximos
        int lenCant = headers[0].length();
        int lenProd = headers[1].length();
        int lenUnit = headers[2].length();
        int lenSubt = headers[3].length();

        for (OrderItem item : cart) {
            lenCant = Math.max(lenCant, String.valueOf(item.getQuantity()).length());
            lenProd = Math.max(lenProd, item.getProduct().getName().length());
            lenUnit = Math.max(lenUnit, String.format("$%.2f", item.getProduct().getPrice()).length());
            double subtotal = item.getQuantity() * item.getProduct().getPrice();
            lenSubt = Math.max(lenSubt, String.format("$%.2f", subtotal).length());
        }

        // Separadores
        String separator = "+" +
                "-".repeat(lenCant + 2) + "+" +
                "-".repeat(lenProd + 2) + "+" +
                "-".repeat(lenUnit + 2) + "+" +
                "-".repeat(lenSubt + 2) + "+";

        // Mostrar encabezado
        System.out.println("ORDEN ID: " + orderId);
        System.out.println(separator);
        System.out.printf("| %" + lenCant + "s | %-" + lenProd + "s | %" + lenUnit + "s | %" + lenSubt + "s |\n",
                headers[0], headers[1], headers[2], headers[3]);
        System.out.println(separator);

        // Mostrar productos
        for (OrderItem item : cart) {
            int qty = item.getQuantity();
            String name = item.getProduct().getName();
            double unitPrice = item.getProduct().getPrice();
            double subtotal = qty * unitPrice;
            total += subtotal;

            System.out.printf("| %" + lenCant + "d | %-" + lenProd + "s | %" + lenUnit + "s | %" + lenSubt + "s |\n",
                    qty, name, String.format("$%.2f", unitPrice), String.format("$%.2f", subtotal));
        }

        // Pie de tabla
        System.out.println(separator);
        System.out.printf("%" + (lenCant + lenProd + lenUnit + lenSubt + 13) + "s\n", "TOTAL: $" + String.format("%,.2f", total));
    }


}