package com.app.product.AppProducts.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "orders",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        }

)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address shippingAddress;

    private String paymentType;

    // ...
    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;



    @PrePersist
    public void prePersist() {
        this.date = new Date(); // Establece la fecha actual antes de persistir el objeto Order
    }

    private double totalOrderValue;

    public Order() {
        this.date = new Date();
        this.orderProducts = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


    public double getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }



    // Getter y Setter de orderProducts
    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }


    public void calculateTotalOrderValue() {
        double totalValue = 0.0;

        for (OrderProduct orderProduct : this.orderProducts) {
            double productPrice = orderProduct.getProduct().getPrice();
            int quantity = orderProduct.getQuantity();

            double orderProductValue = productPrice * quantity;
            totalValue += orderProductValue;
        }

        this.totalOrderValue = totalValue;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", customer=" + customer +
                ", shippingAddress=" + shippingAddress +
                ", paymentType='" + paymentType + '\'' +
                ", orderProducts=" + orderProducts +
                ", totalOrderValue=" + totalOrderValue +
                '}';
    }
}