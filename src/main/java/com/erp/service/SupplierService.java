package com.erp.service;

import java.util.List;

import com.erp.dto.purchase.SupplierDto;
import com.erp.entity.Supplier;
import com.erp.util.dto.GenericResponse;

public interface SupplierService {
	
	GenericResponse saveSupplier(SupplierDto supplierDto);
	List<Supplier> getAllSupplier();
	GenericResponse deleteSupplier(Long Id);

}
