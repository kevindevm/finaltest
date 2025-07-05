package com.techlab.kevin.services;

import com.techlab.kevin.dto.ProductApiResponseDTO;
import com.techlab.kevin.dto.ProductUpdateDTO;
import com.techlab.kevin.entities.Product;
import com.techlab.kevin.exceptions.ProductNotFoundException;
import com.techlab.kevin.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getById(@PathVariable Integer id) {
//        return productRepository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }


public ProductApiResponseDTO<Product> productSearchByKeyword(String keyword) {
    List<Product> found = productRepository.searchByName(keyword);
    int total = (int) productRepository.count();

    if (found.isEmpty()) {
        return new ProductApiResponseDTO<Product>("No products found for keyword: " + keyword);
    }

    return new ProductApiResponseDTO<Product>(keyword, found, total);
}

    public List<Product> productsList() {
        return this.productRepository.findAll();
    }


    public ProductApiResponseDTO<Product> addProduct(Product p) {

        if (p.getStock() < 0) {
            throw new IllegalArgumentException("stock cannot be negative");
        }
        if (p.getName() == null || p.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("product name cannot be empty");
        }
        if (p.getPrice() <= 0) {
            throw new IllegalArgumentException("price must be greater than zero");
        }
        p.setName(p.returnCapitalized(p.getName()));
        if (p.getName().length() > 70) {
            p.setName(p.getName().substring(0, 70));
        }
        if (productRepository.existsByName(p.getName())) {
            throw new IllegalArgumentException("duplicated name");
        }
        Product s = this.productRepository.save(p);
        return new ProductApiResponseDTO<Product>(
                "Producto creado correctamente",
                s.getId()
        );
    }

    public ProductApiResponseDTO<Product> productSearchByID(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));

        return new ProductApiResponseDTO<Product>("Producto encontrado correctamente", product);
    }



    public ResponseEntity<ProductApiResponseDTO<Product>> updateById(Integer id, Product updateData) {
        return productRepository.findById(id).map(existing -> {
            if (updateData.getName() != null) existing.setName(updateData.getName());
            if (updateData.getPrice() > 0) existing.setPrice(updateData.getPrice());
            if (updateData.getStock() >= 0) existing.setStock(updateData.getStock());

            Product updated = productRepository.save(existing);

            ProductApiResponseDTO<Product> response = new ProductApiResponseDTO<Product>(
                    "Producto actualizado correctamente",
                    updated
            );

            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            ProductApiResponseDTO<Product> response = new ProductApiResponseDTO<>(
                    "Producto con ID " + id + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }


    public ResponseEntity<ProductApiResponseDTO<Product>> deleteById(Integer id) {
        if (!productExistByID(id)) {
            ProductApiResponseDTO<Product> response = new ProductApiResponseDTO<Product>(
                    "Producto con ID " + id + " no encontrado"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        productRepository.deleteById(id);

        ProductApiResponseDTO<Product> response = new ProductApiResponseDTO<Product>(
                "Producto eliminado correctamente"
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    public boolean productExistByID(Integer id) {
        return productRepository.existsById(id);

    }


}
