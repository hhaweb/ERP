package com.erp.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.config.ResponseMessage;
import com.erp.dto.sale.PaymentDto;
import com.erp.dto.sale.PaymentListSearchDto;
import com.erp.dto.sale.SaleDto;
import com.erp.dto.sale.SaleHeaderList;
import com.erp.dto.sale.SaleListDto;
import com.erp.dto.sale.SaleListSearchDto;
import com.erp.service.SaleService;
import com.erp.util.ExcelWriter;
import com.erp.util.dto.GenericResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/sale")
public class SaleController {
	@Autowired
	private SaleService saleService;

	@PostMapping("/sale-save")
	public GenericResponse saveSale(@RequestBody SaleDto saleDto) {
		try {
			return saleService.createSale(saleDto);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}

	@PostMapping("/sale-list")
	public SaleListDto getSaleHeaderList(@RequestBody SaleListSearchDto searchModel) {
		try {
			return saleService.getSaleHeaderListByLazyLoad(searchModel);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@GetMapping("/get-sale-by-id")
	public SaleDto getSaleById(@RequestParam("saleId") Long saleId) {
		try {
			SaleDto saleDto = saleService.getSaleById(saleId);
			return saleDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@GetMapping("/delete-sale")
	public GenericResponse delete(@RequestParam("saleId") Long saleId) {
		try {
			return saleService.deleteSale(saleId);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}

	@PostMapping("export-sale-header-list")
	public void exportSaleHeaderList(HttpServletResponse response, @RequestBody SaleListSearchDto searchModel) {
		searchModel.setType("export");
		SaleListDto saleListDto = saleService.getSaleHeaderListByLazyLoad(searchModel);
		ExcelWriter.exportSaleList(response, saleListDto.getSaleItemList(), saleListDto.getTotalCreditAmount(),
				saleListDto.getTotalPayAmount(), saleListDto.getTotalSellAmount());
	}
	@PostMapping("/save-payment")
	public GenericResponse savePayment(@RequestBody PaymentDto paymentDto) {
		try {
			saleService.createPayment(paymentDto);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}
	
	@PostMapping("/payment-list")
	public PaymentDto getPaymentList(@RequestBody PaymentListSearchDto paymentListSearchDto) {
		try {
			return saleService.getPaymentListByLazyLoad(paymentListSearchDto);	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping("/get-payment-id")
	public PaymentDto getPaymentById(@RequestParam("paymentId") Long paymentId) {
		try {
			return saleService.getPaymentById(paymentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@GetMapping("/delete-payment")
	public GenericResponse deletePayment(@RequestParam("paymentId") Long paymentId) {
		try {
			saleService.deletePayment(paymentId);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}
	
}
