package com.app.product.AppProducts.service;

import com.app.product.AppProducts.entity.OrderProduct;
import com.app.product.AppProducts.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderService {
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public ProductOrderService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public OrderProduct saveOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    public void deleteOrderProductsByOrderId(Long id) {
        orderProductRepository.deleteById(id);
    }

}
