package com.erp.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.config.ResponseMessage;
import com.erp.dto.CustomerDto;
import com.erp.entity.Customer;
import com.erp.entity.Supplier;
import com.erp.repository.CustomerRepository;
import com.erp.repository.SaleRepository;
import com.erp.repository.SupplierRepository;
import com.erp.util.dto.GenericResponse;
import com.erp.util.dto.MessageResponse;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private SupplierRepository supplierRepo;
	

	@Override
	public List<Customer> getAllCustomer() {	
		return customerRepo.findAll();
	}

	@Override
	public GenericResponse saveCustomer(CustomerDto customerDto) {
		try {
			Customer customer = customerDto.getEntity();
			customerRepo.save(customer);
			
		} catch (ParseException e) {
			return new GenericResponse(false,ResponseMessage.INTERNAL_ERROR);
		}
		return new GenericResponse(true,ResponseMessage.SAVE_SUCCESS);
	}

	@Override
	public GenericResponse deleteCustomer(Long Id) {
		// TODO Auto-generated method stub
		customerRepo.deleteById(Id);
		return new GenericResponse(false,ResponseMessage.DELETE_SUCCESS);
		
	}

	@Override
	public Customer getCustomerById(Long id) {
		// TODO Auto-generated method stub
		return customerRepo.findById(id).orElse(null);
	}

	@Override
	public Supplier getSupplierById(Long supplierId) {
		// TODO Auto-generated method stub
		return supplierRepo.findById(supplierId).orElse(null);
	}



}
