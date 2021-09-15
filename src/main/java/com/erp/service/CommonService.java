package com.erp.service;

import java.math.BigDecimal;
import java.util.List;

import com.erp.dto.SelectItem;
import com.erp.entity.Customer;
public interface CommonService {
	
	List<SelectItem> getCommonDropDownList(String type);
	List<Customer> getCustomerByName(String name);
	BigDecimal getTotalCredit(Long id);

}
