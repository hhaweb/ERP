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
import com.erp.dto.purchase.ClosingDto;
import com.erp.dto.purchase.PurchaseDto;
import com.erp.dto.purchase.PurchaseHeaderListDto;
import com.erp.dto.purchase.PurchaseHeaderListSearch;
import com.erp.dto.purchase.PurchasePaymentDto;
import com.erp.dto.purchase.PurchasePaymentStoreOutDto;
import com.erp.dto.sale.SaleDto;
import com.erp.entity.purchase.Closing;
import com.erp.service.SupplierService;
import com.erp.service.purchase.PurchaseService;
import com.erp.util.dto.GenericResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
	@Autowired
	SupplierService supplierService;
	@Autowired
	PurchaseService purchaseService;
	
	@PostMapping("/save-purchase")
	public GenericResponse savePurchase(@RequestBody PurchaseDto purchaseDto) {
		try {
			return purchaseService.createPurchase(purchaseDto);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}
	
	@GetMapping("/delete-purchase")
	public GenericResponse deletePurchase(@RequestParam("purchaseId") Long purchaseId) {
		try {
			return purchaseService.deletePurchase(purchaseId);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}
	
	@PostMapping("/get-purchase-header-lazy")
	public PurchaseHeaderListDto getPurchaseHeader(@RequestBody PurchaseHeaderListSearch searchModel) {
		return purchaseService.getPurchaseHeaderViewLazyLoad(searchModel);
	}
	
	@GetMapping("/get-purchase-id")
	public PurchaseDto getPurchaseById(@RequestParam("purchaseId") Long purchaseId) {
		return purchaseService.getPurchaseById(purchaseId);
	}
	
	@PostMapping("/save-purchase-payment")
	public GenericResponse savePurchasePayment(@RequestBody PurchasePaymentDto purchasePaymentDto) {
		try {
			return purchaseService.savePurchasePayment(purchasePaymentDto);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}
	
	@GetMapping("/delete-purchase-payment")
	public GenericResponse deletePurchasePayment(@RequestParam("purchaseId") Long purchasePaymentId) {
		try {
			return purchaseService.deletePurchasePayment(purchasePaymentId);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}
	
	@PostMapping("/get-purchase-payment-lazy")
	public List<PurchasePaymentStoreOutDto> getPurchasePayment(@RequestBody PurchaseHeaderListSearch searchModel) {
		return purchaseService.getPaymentListByLazyLoad(searchModel);
	}
	
	@GetMapping("/get-purchase-payment-id")
	public PurchasePaymentDto getPurchasePaymentById(@RequestParam("purchasePaymentId") long purchasePaymentId) {
		return purchaseService.getPurchasePaymentById(purchasePaymentId);
	}
	
	@GetMapping("/get-all-closing")
	public List<ClosingDto> getAllClosing() {
		return purchaseService.getAllClosing();
	}	
	
	
	
	
}
