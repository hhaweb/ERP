package com.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.config.ResponseMessage;
import com.erp.dto.CustomerDto;
import com.erp.entity.Customer;
import com.erp.service.CustomerService;
import com.erp.util.dto.GenericResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customer-save")
	public GenericResponse saveItem(@RequestBody CustomerDto customer) {
		try {
			customerService.saveCustomer(customer);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);

		}catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}
	
	@GetMapping("/customer-list")
	public List<Customer> getAllItem() {
		return customerService.getAllCustomer();
	} 

}
