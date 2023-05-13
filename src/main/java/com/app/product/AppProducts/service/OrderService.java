package com.app.product.AppProducts.service;

import com.app.product.AppProducts.entity.*;
import com.app.product.AppProducts.exception.*;
import com.app.product.AppProducts.repository.AddressRepository;
import com.app.product.AppProducts.repository.OrderProductRepository;
import com.app.product.AppProducts.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, AddressRepository addressRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) {

        orderRepository.deleteById(id);
    }

    @Transactional
    public Order saveOrder(Order order) {
        if (order == null) {
            throw new NullOrderException("Null order object");
        }
        try {
            order.calculateTotalOrderValue();
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            throw new OrderCreationException("Data integrity violation", e);
        } catch (Exception e) {
            throw new OrderCreationException("An error occurred while creating the order", e);
        }
    }


    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setDate(updatedOrder.getDate());
                    order.setPaymentType(updatedOrder.getPaymentType());


                    for (OrderProduct updatedOrderProduct : updatedOrder.getOrderProducts()) {

                        OrderProduct existingOrderProduct = order.getOrderProducts().stream()
                                .filter(orderProduct -> orderProduct.getId().equals(updatedOrderProduct.getId()))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("OrderProduct with ID " + updatedOrderProduct.getId() + " not found in the existing order"));


                        existingOrderProduct.setQuantity(updatedOrderProduct.getQuantity());
                    }

                    order.setTotalOrderValue(updatedOrder.getTotalOrderValue());
                    order.calculateTotalOrderValue();
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
    }


    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundExceptionbyId("Order with ID " + id + " not found"));
    }
}
