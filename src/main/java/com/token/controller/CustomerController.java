package com.token.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.Service.CustomerService;
import com.token.entity.Customer;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {

		this.customerService = customerService;

	}

	@PostMapping("/register")
	public ResponseEntity<Customer> onboardCustomer(@Valid @RequestBody Customer customer) {
		Customer createdCustomer = customerService.createCustomer(customer);
		return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomerDetails(@PathVariable("customerId") int customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
