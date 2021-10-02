package com.erp.service;

import java.text.ParseException;
import java.util.List;

import com.erp.dto.sale.PaymentDto;
import com.erp.dto.sale.PaymentList;
import com.erp.dto.sale.PaymentListSearchDto;
import com.erp.dto.sale.SaleDto;
import com.erp.dto.sale.SaleListDto;
import com.erp.dto.sale.SaleListSearchDto;
import com.erp.util.dto.GenericResponse;

public interface SaleService {
	
	GenericResponse createSale(SaleDto saleDto);
	GenericResponse deleteSale(Long Id) throws Exception;
	SaleListDto getSaleHeaderListByLazyLoad(SaleListSearchDto searchModel);
	SaleDto getSaleById(Long Id);
	
	GenericResponse createPayment(PaymentDto paymentDto);
	PaymentDto getPaymentListByLazyLoad(PaymentListSearchDto searchModel);
	PaymentDto getPaymentById(Long paymentId);
	GenericResponse deletePayment(Long Id);

	
}
