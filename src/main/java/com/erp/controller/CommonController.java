package com.erp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.SelectItem;
import com.erp.entity.Customer;
import com.erp.entity.Supplier;
import com.erp.service.CommonService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/common")
public class CommonController {
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/get-dropdown-list-by-name")
	public List<SelectItem> getDropDownList(@RequestParam("type") String type){
		return commonService.getCommonDropDownList(type);
	}
	
	@GetMapping("/get-customer-by-name")
	public List<Customer> getCustomerByName(@RequestParam("name") String name) {
		return commonService.getCustomerByName(name);
	}
	
	@GetMapping("/get-total-credit")
	public BigDecimal getTotalCreditValue(@RequestParam("id") Long id) {
		return commonService.getTotalCredit(id);
	}
	
	@GetMapping("/get-total-purchase-credit")
	public BigDecimal getTotalPurchaseCreditValue(@RequestParam("supplierId") Long supplierId) {
		return commonService.getTotalPurchaseCredit(supplierId);
	}
	
	@GetMapping("/get-supplier-by-name")
	public List<Supplier> getSupplierByName(@RequestParam("supplierName") String supplierName) {
		return commonService.getSupplierByName(supplierName);
	}
	
}
