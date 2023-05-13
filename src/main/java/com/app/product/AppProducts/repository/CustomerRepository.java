package com.app.product.AppProducts.repository;

import com.app.product.AppProducts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    Customer findByPhone(String phone);
    List<Customer> findByName(String name);


}
