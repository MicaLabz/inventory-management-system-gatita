package com.inventory.service;

import com.inventory.entity.Product;
import com.inventory.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    public final ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product product) {
        if (productRepository.findByName((product.getName()))) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un producto con ese nombre"
            );
        }
        return productRepository.save(product);
    }

    // Obtener todos los productos
    @Transactional()
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional()
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Producto no encontrado"
                        )
                );
    }



}
