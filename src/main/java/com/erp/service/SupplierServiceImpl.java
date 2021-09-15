package com.erp.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.config.ResponseMessage;
import com.erp.dto.purchase.SupplierDto;
import com.erp.entity.Supplier;
import com.erp.repository.SupplierRepository;
import com.erp.util.dto.GenericResponse;

@Service
public class SupplierServiceImpl implements SupplierService{
	@Autowired
	private SupplierRepository supplierRepo;

	@Override
	public GenericResponse saveSupplier(SupplierDto supplierDto) {
		// TODO Auto-generated method stub
		try {
			Supplier supplier = supplierDto.getEntity();
			supplierRepo.save(supplier);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GenericResponse(true, ResponseMessage.INTERNAL_ERROR);
		}
	}

	@Override
	public List<Supplier> getAllSupplier() {
		// TODO Auto-generated method stub
		return supplierRepo.findAll();
	}

	@Override
	public GenericResponse deleteSupplier(Long Id) {
		supplierRepo.deleteById(Id);
		return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
	}

}
