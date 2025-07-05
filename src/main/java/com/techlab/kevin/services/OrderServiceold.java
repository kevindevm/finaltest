package com.techlab.kevin.services;

import com.techlab.kevin.entities.Orders;
import com.techlab.kevin.utils.utils;

import java.util.ArrayList;

public class OrderServiceold {
    static ArrayList<Orders> allOrders = new ArrayList<>();

    public ArrayList<Orders> getAllOrders() {
        return allOrders;
    }

     public static void listOrders() {
        utils.showTitle("Listar Pedidos");
        if (allOrders.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        for (Orders o : allOrders) {
//            System.out.println("\n🧾 Pedido ID: " + o.getID());
            Orders.showCart(o.getID(),o.getCart());
        }
    }

//    public static void createOrder() throws NoStockException {
//         utils.showTitle("Crear Pedido");
//        Scanner scanner = new Scanner(System.in);
//        orders currentOrder = new orders();
//        allOrders.add(currentOrder);
//        System.out.println("Nuevo pedido creado con ID: " + currentOrder.getID());
//
//
//        boolean keepOnAdding = true;
//
//        while (keepOnAdding) {
//            System.out.println("Ingrese el ID o nombre del producto a agregar a la orden:");
//            product selectedProduct = productService.productSearchByID(scanner);
//            if (selectedProduct == null) {
//                System.out.println("❌ Producto no encontrado.");
//                continue;
//            }
//            if (selectedProduct.getStock() <= 0) {
//                System.out.println("❌ Producto sin stock");
//                return;
//            }
//
//            int cantidad = utils.ingresarCantidad(scanner, selectedProduct);
//            if (cantidad == -1) continue;
//
//            currentOrder.addItem(selectedProduct, cantidad);
//            try {
//                selectedProduct.updateStock(-cantidad);
//            }catch (NoStockException e){
//                throw new NoStockException("No hay stock suficiente para el producto con ID: " + selectedProduct.getId());
//            }
//
//            System.out.println("✅ Producto agregado al carrito ID " + currentOrder.getID());
//
//            System.out.println("¿Desea agregar otro producto? (s/n):");
//            keepOnAdding = scanner.nextLine().equalsIgnoreCase("s");
//        }
//    }





}
