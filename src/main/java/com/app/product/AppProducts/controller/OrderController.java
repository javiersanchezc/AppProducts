package com.app.product.AppProducts.controller;

import com.app.product.AppProducts.controller.response.ApiResponseOrder;
import com.app.product.AppProducts.entity.Address;
import com.app.product.AppProducts.entity.Order;
import com.app.product.AppProducts.entity.OrderProduct;
import com.app.product.AppProducts.exception.*;
import com.app.product.AppProducts.service.AddressService;
import com.app.product.AppProducts.service.OrderService;
import com.app.product.AppProducts.service.ProductOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final AddressService addressService;
    private  final ProductOrderService productOrderService;

    @Autowired
    public OrderController(OrderService orderService, AddressService addressService, ProductOrderService productOrderService) {
        this.orderService = orderService;
        this.addressService = addressService;
        this.productOrderService = productOrderService;
    }

    @Operation(summary = "Crea una nueva orden",
            description = "Crea una nueva orden y la devuelve.",
            tags = { "order" })
    @ApiResponse(responseCode = "200", description = "La orden fue creada exitosamente",
            content = @Content(schema = @Schema(implementation = Order.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
            content = @Content)
    @ApiResponse(responseCode = "500", description = "Error del servidor",
            content = @Content)
    @PostMapping
    public Order createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Detalles de la orden para crear",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Order.class),
                            examples = {
                                    @ExampleObject(
                                            name = "order",
                                            value = "{\n  \"id\": 1,\n  \"date\": \"2023-05-18T12:00:00\",\n  \"customer\": {\n    \"id\": 1,\n    \"name\": \"Nombre del cliente\",\n    \"phone\":\"1237896\",\n    \"email\": \"correo@example.com\"\n  },\n  \"shippingAddress\": {\n    \"id\": 1,\n    \"street\": \"restrepo\",\n    \"city\": \"Ciudad\",\n    \"country\": \"País\",\n    \"state\": \"Estado\",\n    \"zipCode\": \"Código Postal\"\n  },\n  \"paymentType\": \"cheque\",\n  \"orderProducts\": [\n    {\n      \"id\": 1,\n      \"order\": {\n        \"id\": 1\n      },\n      \"product\": {\n        \"id\": 1,\n        \"description\": \"el producto\",\n        \"price\": 9.99,\n        \"weight\": 20 \n      },\n      \"quantity\": 20\n    }\n  ],\n  \"totalOrderValue\": 2500\n}"
                                    )
                            }
                    )
            )
            @Valid @RequestBody Order order) {
        if (order == null) {
            throw new NullOrderException("Null order object");
        }

        try {
            return orderService.saveOrder(order);
        } catch (DataIntegrityViolationException e) {
            throw new OrderCreationException("Data integrity violation", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrderCreationException("An error occurred while creating the order", e);
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();

            if(orders.isEmpty()) {
                throw new NoOrdersFoundException("No orders found");
            }

            return orders;
        } catch (NoOrdersFoundException e) {
            // podrías hacer algún manejo adicional de la excepción aquí si es necesario
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while getting all orders", e);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseOrder> deleteOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);

        if(order == null){
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }

        try {
            productOrderService.deleteOrderProductsByOrderId(id); // Delete order_products records by order ID
            orderService.deleteOrder(id); // Delete the order
            return ResponseEntity.ok(new ApiResponseOrder(true, "Order with ID " + id + " deleted successfully."));
        } catch (DataAccessException e) {
            throw new OrderDeletionException("An error occurred while deleting the order with ID " + id, e);
        }
    }


    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @Valid @RequestBody Order updatedOrder) {
        return orderService.updateOrder(id, updatedOrder);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            throw new OrderNotFoundExceptionbyId("Order with ID " + id + " not found");
        }
        return order;
    }

}
