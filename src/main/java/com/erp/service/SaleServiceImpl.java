package com.erp.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.config.ResponseMessage;
import com.erp.dto.sale.PaymentDto;
import com.erp.dto.sale.PaymentList;
import com.erp.dto.sale.PaymentListSearchDto;
import com.erp.dto.sale.SaleDto;
import com.erp.dto.sale.SaleHeaderList;
import com.erp.dto.sale.SaleListDto;
import com.erp.dto.sale.SaleListSearchDto;
import com.erp.entity.Customer;
import com.erp.entity.sale.Payment;
import com.erp.entity.sale.Sale;
import com.erp.entity.sale.SaleItem;
import com.erp.repository.PaymentRepository;
import com.erp.repository.SaleRepository;
import com.erp.util.dto.GenericResponse;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private PaymentRepository paymentRepo;

	@Override
	public GenericResponse createSale(SaleDto saleDto) {
		// TODO Auto-generated method stub
		try {
			Sale sale = saleDto.getEntity();
			Sale saveObj = saleRepo.saveAndFlush(sale);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String orderDate = df.format(saveObj.getOrderDate());
			PaymentDto paymentDto = new PaymentDto(saveObj.getCustomer().getId(), orderDate, saveObj.getDebit(),
					sale.getId());
			Payment payment = paymentDto.getEntity();
			paymentRepo.save(payment);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}

	@Override
	public GenericResponse deleteSale(Long Id) {
		// TODO Auto-generated method stub
		saleRepo.deleteById(Id);
		return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
	}

	@Override
	public SaleListDto getSaleHeaderListByLazyLoad(SaleListSearchDto searchModel) {
		SaleListDto saleListDto = new SaleListDto();
		String customerId = searchModel.getCustomerId() != null ? String.join(",", searchModel.getCustomerId()) : null;
		String orderDate = searchModel.getOrderDate();
		int pagePerRow = searchModel.getPagePerRow();
		int pageNumber = searchModel.getPageNumber();
		String sortName = searchModel.getSortName();
		int sortOrder = searchModel.getSortOrder();

		List<SaleHeaderList> saleHeaderList = saleRepo.getSaleListHeaderViewLazyLoad(customerId, orderDate, pagePerRow,
				pageNumber, sortName, sortOrder);

		long[] customerIdList = saleHeaderList.stream().mapToLong(SaleHeaderList::getCustomerId).distinct().toArray();
		if (customerIdList.length > 0) {
			saleListDto.setSaleItemList(saleHeaderList);
			BigDecimal totalCredit = BigDecimal.ZERO;
			BigDecimal totalDebit = BigDecimal.ZERO;
			BigDecimal totalSellAmount = BigDecimal.ZERO;
			for (Long id : customerIdList) {
				totalDebit = totalDebit.add(saleRepo.getTotalPayAmount(id));
				totalSellAmount = totalSellAmount.add(saleRepo.getTotalSaleAmount(id));				
			}
			totalCredit = totalSellAmount.subtract(totalDebit);
			saleListDto.setTotalCredit(totalCredit);
			saleListDto.setTotalDebit(totalDebit);
			saleListDto.setTotalSellAmount(totalSellAmount);
		}

		return saleListDto;
	}

	@Override
	public SaleDto getSaleById(Long Id) {
		Sale sale = saleRepo.getSaleById(Id);
		BigDecimal totalCredit = BigDecimal.ZERO;
		BigDecimal totalSaleAmount = saleRepo.getTotalSaleAmount(sale.getCustomer().getId());
		BigDecimal totalPayAmount = saleRepo.getTotalPayAmount(sale.getCustomer().getId());
		totalCredit = totalSaleAmount.subtract(totalPayAmount);
		SaleDto saleDto = new SaleDto(sale, totalCredit, BigDecimal.ZERO, BigDecimal.ZERO);
		return saleDto;
	}

	@Override
	public GenericResponse createPayment(PaymentDto paymentDto) {
		try {
			Payment payment = paymentDto.getEntity();
			paymentRepo.save(payment);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}

	@Override
	public PaymentDto getPaymentListByLazyLoad(PaymentListSearchDto searchModel) {
		String customerId = searchModel.getCustomerId() != null ? String.join(",", searchModel.getCustomerId()) : null;
		String payDate = searchModel.getPayDate();
		int pagePerRow = searchModel.getPagePerRow();
		int pageNumber = searchModel.getPageNumber();
		String sortName = searchModel.getSortName();
		int sortOrder = searchModel.getSortOrder();
		String type = null;
		if(searchModel.getType() != null && searchModel.getType().size() == 1) {
			type = searchModel.getType().get(0);
		}
		String remark = searchModel.getRemark();
		List<PaymentList> paymentList = paymentRepo.getPaymentListByLazyLoad(customerId, payDate, type,remark, pagePerRow,
				pageNumber, sortName, sortOrder);
		long[] customerIdList = paymentList.stream().mapToLong(PaymentList::getCustomerId).distinct().toArray();
		BigDecimal totalPayAmount = BigDecimal.ZERO;
		for (Long id : customerIdList) {
			totalPayAmount = totalPayAmount.add(saleRepo.getTotalPayAmount(id));
		}
		PaymentDto paymentDto = new PaymentDto(paymentList, totalPayAmount);
		return paymentDto;
	}

	@Override
	public GenericResponse deletePayment(Long Id) {
		// TODO Auto-generated method stub
		paymentRepo.deleteById(Id);
		return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
	}

}
