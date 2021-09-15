package com.erp.service;

import java.util.List;


import com.erp.dto.CustomerDto;
import com.erp.entity.Customer;
import com.erp.util.dto.GenericResponse;

public interface CustomerService {
	List<Customer> getAllCustomer();
	GenericResponse saveCustomer(CustomerDto customerDto);
	GenericResponse deleteCustomer(Long Id);
	Customer getCustomerById(Long id);
	
}
