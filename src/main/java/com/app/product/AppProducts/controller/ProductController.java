package com.app.product.AppProducts.controller;

import com.app.product.AppProducts.controller.response.MessageResponseProduct;
import com.app.product.AppProducts.entity.Product;
import com.app.product.AppProducts.exception.ProductCreationException;
import com.app.product.AppProducts.exception.ProductNotFoundException;

import com.app.product.AppProducts.exception.ProductUpdateException;
import com.app.product.AppProducts.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product")
    public Product createProduct(@Valid @org.springframework.web.bind.annotation.RequestBody Product product) {
        try {
            return productService.saveProduct(product);
        } catch (DataIntegrityViolationException e) {
            throw new ProductCreationException("Data integrity violation", e);
        } catch (Exception e) {
            throw new ProductCreationException("An error occurred while creating the product", e);
        }
    }

    @GetMapping
    public List<Product> getProducts() {
        List<Product> products = productService.getProducts();
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products found.");
        }
        return products;
    }


    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        return product;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public Product updateProduct(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody Product updatedProduct) {
        Product existingProduct = productService.getProduct(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        try {
            return productService.updateProduct(id, updatedProduct);
        } catch (DataIntegrityViolationException e) {
            throw new ProductUpdateException("Data integrity violation", e);
        } catch (Exception e) {
            throw new ProductUpdateException("An error occurred while updating the product", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseProduct> deleteProduct(@PathVariable Long id) {
        Product existingProduct = productService.getProduct(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        productService.deleteProduct(id);

        // Retorna una respuesta exitosa cuando el producto se elimina correctamente
        return ResponseEntity.ok(new MessageResponseProduct("Product with ID " + id + " was deleted successfully."));
    }

    @GetMapping("/search/{description}")
    public List<Product> searchProducts(@PathVariable String description){
        List<Product> products = productService.searchProducts(description);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found with description " + description);
        }
        return products;
    }
}
