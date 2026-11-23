package com.inventory.controller;

import com.inventory.dto.ProductRequest;
import com.inventory.dto.ProductResponse;
import com.inventory.dto.ProductUpdateRequest;
import com.inventory.entity.Product;
import com.inventory.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Listar todos los productos
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Buscar producto por ID
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return convertToResponse(product);
    }

    // Crear producto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(
            @Valid @RequestBody ProductRequest request) {

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setMinStock(request.minStock());

        Product savedProduct = productService.createProduct(product);

        return convertToResponse(savedProduct);
    }

    // Convertir Entity a DTO
    private ProductResponse convertToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getMinStock(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {

        Product updatedProduct =
                productService.updateProduct(id, request);

        return convertToResponse(updatedProduct);
    }

    // Actualizar producto
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }


}
