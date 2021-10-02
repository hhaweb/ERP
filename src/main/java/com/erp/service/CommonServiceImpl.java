package com.erp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dto.SelectItem;
import com.erp.entity.Customer;
import com.erp.entity.Supplier;
import com.erp.repository.CommonRepository;
import com.erp.repository.CustomerRepository;
import com.erp.repository.SaleRepository;
import com.erp.repository.SupplierRepository;
import com.erp.repository.purchase.PurchasePaymentRepository;
import com.erp.repository.purchase.PurchaseRepository;
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonRepository commonRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private PurchaseRepository purchaseRepo;
	@Autowired
	private PurchasePaymentRepository purchasePaymentRepo;
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Override
	public List<SelectItem> getCommonDropDownList(String type) {
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		if(type.equalsIgnoreCase("item")) {
			selectItemList = commonRepo.getItemNameList();
		} else if(type.equalsIgnoreCase("customer")) {
			selectItemList = commonRepo.getCustomerNameList();
		} else if(type.equalsIgnoreCase("supplier")) {
			selectItemList = commonRepo.getSupplierNameList();			
		}
		return selectItemList;
	}

	@Override
	public List<Customer> getCustomerByName(String name) {
		// TODO Auto-generated method stub
		return customerRepo.findByNameIgnoreCaseContaining(name);
	}

	@Override
	public BigDecimal getTotalCredit(Long id) {
		// TODO Auto-generated method stub
		BigDecimal totalSaleAmount = saleRepo.getTotalSaleAmount(id);
		BigDecimal totalPayAmount = saleRepo.getTotalPayAmount(id);	
		return totalSaleAmount.subtract(totalPayAmount);
	}

	@Override
	public BigDecimal getTotalPurchaseCredit(Long supplierId) {
		BigDecimal buyTotal = purchaseRepo.getTotalBuyAmount(supplierId);
		BigDecimal payTotal = purchasePaymentRepo.getTotalPayAmount(supplierId);
		return buyTotal.subtract(payTotal);
	}

	@Override
	public List<Supplier> getSupplierByName(String supplierName) {
		// TODO Auto-generated method stub
		return supplierRepo.findByNameIgnoreCaseContaining(supplierName);
	}
}
