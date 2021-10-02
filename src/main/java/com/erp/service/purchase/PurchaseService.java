package com.erp.service.purchase;

import java.text.ParseException;
import java.util.List;

import com.erp.dto.purchase.ClosingDto;
import com.erp.dto.purchase.PurchaseDto;
import com.erp.dto.purchase.PurchaseHeaderListDto;
import com.erp.dto.purchase.PurchaseHeaderListSearch;
import com.erp.dto.purchase.PurchaseHeaderStoreOutDto;
import com.erp.dto.purchase.PurchasePaymentDto;
import com.erp.dto.purchase.PurchasePaymentStoreOutDto;
import com.erp.dto.sale.PaymentList;
import com.erp.entity.purchase.Closing;
import com.erp.util.dto.GenericResponse;

public interface PurchaseService {
	
	GenericResponse createPurchase(PurchaseDto purchaseDto) throws Exception;
	GenericResponse deletePurchase(long purchaseId) throws Exception;
	PurchaseDto getPurchaseById(long purchaseId);
	
	PurchaseHeaderListDto getPurchaseHeaderViewLazyLoad(PurchaseHeaderListSearch searchModel);
	
	List<PurchasePaymentStoreOutDto> getPaymentListByLazyLoad(PurchaseHeaderListSearch searchModel);

	PurchasePaymentDto getPurchasePaymentById(long purchasePaymentId);
	
	List<ClosingDto> getAllClosing();
	
	GenericResponse savePurchasePayment(PurchasePaymentDto purchasePayment) throws Exception;
	
	GenericResponse deletePurchasePayment(long purchasePaymentId);
	
	Closing getClosingByType(Long itemId);
	
	
}
