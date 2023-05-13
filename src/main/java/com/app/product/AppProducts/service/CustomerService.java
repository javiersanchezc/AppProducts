package com.app.product.AppProducts.service;

import com.app.product.AppProducts.controller.CustomerController;
import com.app.product.AppProducts.entity.Address;
import com.app.product.AppProducts.entity.Customer;
import com.app.product.AppProducts.exception.ResourceNotFoundException;
import com.app.product.AppProducts.repository.AddressRepository;
import com.app.product.AppProducts.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private com.app.product.AppProducts.repository.AddressRepository addressRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer updateCustomer(Long id, Customer customer) {

        Customer cliente = customerRepository.findById(id).orElse(null);
        Address direcion = addressRepository.findById(id).orElse(null);



        cliente.setName(customer.getName());
        cliente.setPhone(customer.getPhone());
        cliente.setEmail(customer.getEmail());


        direcion.setCity(customer.getPrimaryAddress().getCity());
        direcion.setStreet(customer.getPrimaryAddress().getStreet());
        direcion.setState(customer.getPrimaryAddress().getState());
        direcion.setZipCode(customer.getPrimaryAddress().getZipCode());
        direcion.setCountry(customer.getPrimaryAddress().getCountry());
        customerRepository.save(cliente);
        addressRepository.save(direcion);




return null;
    }

    public void deleteCustomer(Long id) {

        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Customer not found with id " + id);
        }
    }

    public List<Customer> searchCustomers(String name) {
        return customerRepository.findByName(name);
    }
}
