package com.token.Service;

import org.springframework.stereotype.Service;

import com.token.entity.Customer;
import com.token.repository.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository=customerRepository;
	}
	
	public Customer onboardCustomer(String mobileNumber , String primaryAccount) {
		Customer customer = new Customer();
		customer.setMobileNumber(mobileNumber);
		return customerRepository.save(customer);
	}
	public Customer getCustomerById(int customerId) {
		return customerRepository.findById(customerId).orElse(null);
	}

	public Customer createCustomer(Customer customer) {
		
		return customerRepository.save(customer);
	}
}
