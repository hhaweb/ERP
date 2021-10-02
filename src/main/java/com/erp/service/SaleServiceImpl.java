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
import com.erp.dto.purchase.ClosingDto;
import com.erp.dto.purchase.PurchasePaymentDto;
import com.erp.dto.sale.PaymentDto;
import com.erp.dto.sale.PaymentList;
import com.erp.dto.sale.PaymentListSearchDto;
import com.erp.dto.sale.SaleDto;
import com.erp.dto.sale.SaleHeaderList;
import com.erp.dto.sale.SaleItemDto;
import com.erp.dto.sale.SaleListDto;
import com.erp.dto.sale.SaleListSearchDto;
import com.erp.entity.Customer;
import com.erp.entity.purchase.Closing;
import com.erp.entity.purchase.Purchase;
import com.erp.entity.purchase.PurchaseItem;
import com.erp.entity.purchase.PurchasePayment;
import com.erp.entity.sale.Payment;
import com.erp.entity.sale.Sale;
import com.erp.entity.sale.SaleItem;
import com.erp.repository.PaymentRepository;
import com.erp.repository.SaleItemRepository;
import com.erp.repository.SaleRepository;
import com.erp.repository.purchase.ClosingRepository;
import com.erp.repository.purchase.PurchasePaymentRepository;
import com.erp.util.dto.GenericResponse;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private ClosingRepository closingRepository;
	@Autowired
	private SaleItemRepository saleItemRepo;

	private void updateClosing(List<SaleItemDto> saleItemDtoList) throws ParseException {
		for (SaleItemDto newSaleItemDto : saleItemDtoList) {
			Closing existingClosing = newSaleItemDto.getItemId() != null ? closingRepository.getClosingByItemId(newSaleItemDto.getItemId()) : null;
			if (existingClosing != null) { // update closing;
				BigDecimal newQty =  existingClosing.getQty();
				SaleItem existingSaleItem = newSaleItemDto.getId() != null ? saleItemRepo.findById(newSaleItemDto.getId()).orElse(null) : null;
				if(existingSaleItem != null) { // sale item edit
					int compare = newSaleItemDto.getQty().compareTo(existingSaleItem.getQty());
					if(compare == 1) { // update with increase qty 
						BigDecimal increaseQty = newSaleItemDto.getQty().subtract(existingSaleItem.getQty()); // extraUpdateAmont = new Qty - old Qty
						newQty = existingClosing.getQty().subtract(increaseQty);// closing qty will reduce
					} else if(compare == -1) { // update with reduce qty
						BigDecimal reduceQty = existingSaleItem.getQty().subtract(newSaleItemDto.getQty()); // extraUpdateAmont = new Qty - old Qty
						newQty = existingClosing.getQty().add(reduceQty);  // closing qty will increase
					}	
				} else { // sale item new
					newQty = existingClosing.getQty().subtract(newSaleItemDto.getQty());
				}	
			
				existingClosing.setQty(newQty); // set new qty
				existingClosing.setUpdateDate(new Date()); // set update date
				closingRepository.save(existingClosing);
			}
			
			
		}
	}

	private void updatePayment(Sale sale, BigDecimal payAmount) throws ParseException {
		Payment existingPayment = paymentRepo.findBySale(sale);
		if (existingPayment != null) {
			existingPayment.setPayAmount(payAmount); // update pay amount
			existingPayment.updateDate = new Date();
			int payAmountCheck = existingPayment.getPayAmount().compareTo(payAmount); // check pay amount
			if (payAmountCheck != 0) {
				existingPayment.setPayAmount(payAmount);
			}
			paymentRepo.save(existingPayment);
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			PaymentDto paymentDto = new PaymentDto(sale.getCustomer().getId(), df.format(sale.getOrderDate()),
					payAmount, sale.getId());
			Payment payment = paymentDto.getEntity();
			paymentRepo.save(payment);
		}
	}

	private boolean checkClosing(List<SaleItemDto> saleItemDtoList, boolean isNew) {
		List<Closing> closingList = closingRepository.findAll();
		for (SaleItemDto saleItemDto : saleItemDtoList) {
			Closing closing = closingList.stream().filter(c -> c.getItem().Id == saleItemDto.getItemId()).findFirst()
					.orElse(null);
			if (isNew) { // for new sale item saving
				if (closing != null) {
					int comapre = closing.getQty().compareTo(saleItemDto.getQty());
					return (comapre == 0 || comapre == 1) ? true : false;
				} else {
					return false;
				}

			} else { // for edit item
				SaleItem existingSaleItem = saleItemRepo.findById(saleItemDto.getId()).orElse(null);
				int comapre = existingSaleItem.getQty().compareTo(saleItemDto.getQty());
				if (comapre == 0 || comapre == 1) { // edit with same qty or reduce qty;
					return true;
				} else { // edit with more qty
					BigDecimal extraUpdateAmont = saleItemDto.getQty().subtract(existingSaleItem.getQty()); // extraUpdateAmont = new Qty - old Qty
					int compare = closing.getQty().compareTo(extraUpdateAmont);
					return (compare == 0 || compare == 1) ? true : false;
				}
			}
		}
		return false;
	}

	@Override
	public GenericResponse createSale(SaleDto saleDto) {
		// TODO Auto-generated method stub
		try {

			Sale existingSale = saleDto.getId() != null ? saleRepo.findById(saleDto.getId()).orElse(null) : null;
			boolean isNew = existingSale != null ? false : true;
			
			if (checkClosing(saleDto.getSaleItemListDto(), isNew) == true) {

				updateClosing(saleDto.getSaleItemListDto());
				Sale sale = saleDto.getEntity();
				Sale saveObj = saleRepo.saveAndFlush(sale);				
				updatePayment(saveObj, saleDto.getPayAmount());
				return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
			} else {
				return new GenericResponse(false, ResponseMessage.INSUFFICIENT_QTY);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}

	@Override
	public GenericResponse deleteSale(Long Id) throws Exception {
		// TODO Auto-generated method stub
		Sale existingSale = saleRepo.findById(Id).orElse(null);
		if (existingSale != null) {
			Payment existingPayment = paymentRepo.findBySale(existingSale);
			paymentRepo.delete(existingPayment);
			List<SaleItem> saleItemList = saleItemRepo.getSaleItemBySaleId(Id);
			for(SaleItem saleItem: saleItemList) {
				Closing existingClosing = closingRepository.getClosingByItemId(saleItem.getItem().getId());
				if(existingClosing != null) {
					existingClosing.setQty(existingClosing.getQty().add(saleItem.getQty())); // add delete qty to closing
					closingRepository.save(existingClosing);
				}		
			}
			saleRepo.deleteById(Id);
			return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
		}
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
			BigDecimal totalPayAmount = BigDecimal.ZERO;
			BigDecimal totalCreditAmount = BigDecimal.ZERO;
			BigDecimal totalSellAmount = BigDecimal.ZERO;
			for (Long id : customerIdList) {
				totalPayAmount = totalPayAmount.add(saleRepo.getTotalPayAmount(id));
				totalSellAmount = totalSellAmount.add(saleRepo.getTotalSaleAmount(id));
			}
			totalCreditAmount = totalSellAmount.subtract(totalPayAmount);
			saleListDto.setTotalCreditAmount(totalCreditAmount);
			saleListDto.setTotalPayAmount(totalPayAmount);
			saleListDto.setTotalSellAmount(totalSellAmount);
		}

		return saleListDto;
	}

	@Override
	public SaleDto getSaleById(Long Id) {
		Sale sale = saleRepo.getSaleById(Id);
		BigDecimal previousTotalCreditAmount = BigDecimal.ZERO;
		BigDecimal totalSaleAmount = saleRepo.getTotalSaleAmount(sale.getCustomer().getId());
		BigDecimal totalPayAmount = saleRepo.getTotalPayAmount(sale.getCustomer().getId());
		previousTotalCreditAmount = totalSaleAmount.subtract(totalPayAmount);
		
		BigDecimal payAmount = BigDecimal.ZERO;
		Payment payment = paymentRepo.findBySale(sale);
		if(payment != null) {
			payAmount = payment.getPayAmount();
		}
		
		SaleDto saleDto = new SaleDto(sale, previousTotalCreditAmount, payAmount);
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
		if (searchModel.getType() != null && searchModel.getType().size() == 1) {
			type = searchModel.getType().get(0);
		}
		String remark = searchModel.getRemark();
		List<PaymentList> paymentList = paymentRepo.getPaymentListByLazyLoad(customerId, payDate, type, remark,
				pagePerRow, pageNumber, sortName, sortOrder);
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

	@Override
	public PaymentDto getPaymentById(Long paymentId) {
		Payment payment = paymentRepo.findById(paymentId).orElse(null);
		if (payment != null) {
			PaymentDto paymentDto = new PaymentDto(payment);
			return paymentDto;
		}

		return null;
	}

}
