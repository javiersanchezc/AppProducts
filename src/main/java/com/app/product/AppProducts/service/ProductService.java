package com.app.product.AppProducts.service;

import com.app.product.AppProducts.entity.Product;
import com.app.product.AppProducts.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProduct(id);
        if (product != null) {
            // Actualizar los detalles del producto existente
            product.setDescription(productDetails.getDescription());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setWeight(productDetails.getWeight());

            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {

        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String description) {
        return (List<Product>) productRepository.findByDescription(description);
    }
}
