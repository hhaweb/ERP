package com.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.config.ResponseMessage;
import com.erp.dto.CustomerDto;
import com.erp.dto.purchase.SupplierDto;
import com.erp.entity.Customer;
import com.erp.entity.Supplier;
import com.erp.service.CustomerService;
import com.erp.service.SupplierService;
import com.erp.util.dto.GenericResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private SupplierService supplierService;

	@PostMapping("/customer-save")
	public GenericResponse saveItem(@RequestBody CustomerDto customer) {
		try {
			customerService.saveCustomer(customer);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR); 
		}
	}

	@GetMapping("/customer-list")
	public List<Customer> getAllCustomer() {
		return customerService.getAllCustomer();
	}

	@GetMapping("/customer-delete")
	public GenericResponse deleteCustomer(@RequestParam("Id") Long Id) {
		return customerService.deleteCustomer(Id);
	}

	@PostMapping("/supplier-save")
	public GenericResponse saveSupplier(@RequestBody SupplierDto supplierDto) {
		try {
			supplierService.saveSupplier(supplierDto);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);

		}
	}

	@GetMapping("/supplier-list")
	public List<Supplier> getAllSupplier() {
		return supplierService.getAllSupplier();

	}

	@GetMapping("/supplier-delete")
	public GenericResponse deleteSupplier(Long Id) {
		try {
			supplierService.deleteSupplier(Id);
			return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}

	@GetMapping("/get-customer-by-id")
	public Customer getCustomerById(@RequestParam("id") Long id) {
		Customer customer = customerService.getCustomerById(id);
		return customer;
	}

	@GetMapping("/get-supplier-by-id")
	public Supplier getSupplierById(@RequestParam("supplierId") Long supplierId) {
		return customerService.getSupplierById(supplierId);
	}
}
