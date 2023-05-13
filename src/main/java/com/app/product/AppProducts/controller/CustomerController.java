package com.app.product.AppProducts.controller;


import com.app.product.AppProducts.controller.response.ApiResponseCustumer;
import com.app.product.AppProducts.entity.Customer;
import com.app.product.AppProducts.exception.CustomerNotFoundException;
import com.app.product.AppProducts.exception.CustomerUpdateException;
import com.app.product.AppProducts.exception.NoCustomersFoundException;
import com.app.product.AppProducts.exception.ResourceNotFoundException;
import com.app.product.AppProducts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    // Create a Logger
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping
    @Operation(summary = "Create a new customer",
            requestBody = @RequestBody(content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(name = "customer",
                            value = "{\n" +
                                    "  \"name\": \"Customer 1\",\n" +
                                    "  \"phone\": \"123456890\",\n" +
                                    "  \"email\": \"customer1@example.com\",\n" +
                                    "  \"primaryShippingAddress\": {\n" +
                                    "    \"street\": \"5678 Secondary St\",\n" +
                                    "    \"city\": \"San Francisco\",\n" +
                                    "    \"state\": \"CA\",\n" +
                                    "    \"zipCode\": \"94101\",\n" +
                                    "    \"country\": \"USA\"\n" +
                                    "  }\n" +
                                    "}", summary = "Example of a new customer"))))

    public Customer createCustomer(@Valid @org.springframework.web.bind.annotation.RequestBody Customer customer){
        try {
            return customerService.saveCustomer(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation - this customer may already exist.");
        }

    }

    @GetMapping
    public List<Customer> getCustomers(){

        List<Customer> customers = customerService.getCustomers();
        if (customers.isEmpty()) {
            throw new NoCustomersFoundException("No customers found");
        }
        return customers;
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id){
        return customerService.getCustomer(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseCustumer> updateCustomer(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customer);
            return ResponseEntity.ok(new ApiResponseCustumer(true, "Customer updated successfully.", updatedCustomer));
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        } catch (DataIntegrityViolationException e) {
            throw new CustomerUpdateException("Data integrity violation", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the customer", e);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Customer successfully deleted.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while deleting the customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the customer.");
        }
    }


    @GetMapping("/search/{name}")
    public List<Customer> searchCustomers(@PathVariable String name){

        List<Customer> customers = customerService.searchCustomers(name);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer with name " + name + " not found");
        }
        return customers;


    }
}
