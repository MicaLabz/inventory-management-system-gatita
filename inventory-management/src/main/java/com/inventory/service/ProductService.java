package com.inventory.service;

import com.inventory.dto.ProductUpdateRequest;
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
        if (productRepository.existsByName((product.getName()))) {
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

    @Transactional
    public Product updateProduct(Long id, ProductUpdateRequest request) {

        // 1. Buscar el producto existente
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró el producto con ID: " + id
                ));

        // 2. Comprobar si otro producto utiliza el mismo name
        boolean nameExists = productRepository
                .existsByNameAndIdNot(request.name(), id);

        if (nameExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El nombre ya está registrado en otro producto"
            );
        }

        // 3. Actualizar los atributos
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setMinStock(request.minStock());

        // 4. Guardar los cambios
        return productRepository.save(product);
    }

    @Transactional
    public void deleteById(Long Id) {
        Product product = productRepository.findById(Id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado con ID: " + Id
                ));

        productRepository.delete(product);
    }


}
