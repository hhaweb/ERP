package com.erp.service;

import java.math.BigDecimal;
import java.util.List;

import com.erp.dto.SelectItem;
import com.erp.entity.Customer;
import com.erp.entity.Supplier;
public interface CommonService {
	
	List<SelectItem> getCommonDropDownList(String type);
	List<Customer> getCustomerByName(String name);
	BigDecimal getTotalCredit(Long id);
	BigDecimal getTotalPurchaseCredit(Long supplierId);
	List<Supplier> getSupplierByName(String supplierName);

}
