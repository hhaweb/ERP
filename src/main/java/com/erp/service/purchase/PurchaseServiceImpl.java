package com.erp.service.purchase;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.config.ResponseMessage;
import com.erp.dto.purchase.ClosingDto;
import com.erp.dto.purchase.PurchaseDto;
import com.erp.dto.purchase.PurchaseHeaderListDto;
import com.erp.dto.purchase.PurchaseHeaderListSearch;
import com.erp.dto.purchase.PurchaseHeaderStoreOutDto;
import com.erp.dto.purchase.PurchaseItemDto;
import com.erp.dto.purchase.PurchasePaymentDto;
import com.erp.dto.purchase.PurchasePaymentStoreOutDto;
import com.erp.entity.purchase.Closing;
import com.erp.entity.purchase.Purchase;
import com.erp.entity.purchase.PurchaseItem;
import com.erp.entity.purchase.PurchasePayment;
import com.erp.entity.sale.SaleItem;
import com.erp.repository.purchase.ClosingRepository;
import com.erp.repository.purchase.PurchaseItemRepository;
import com.erp.repository.purchase.PurchasePaymentRepository;
import com.erp.repository.purchase.PurchaseRepository;
import com.erp.util.dto.GenericResponse;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	@Autowired
	private PurchaseRepository purchaseRepo;
	@Autowired
	private PurchasePaymentRepository purchasePaymentRepo;
	@Autowired
	private PurchaseItemRepository purchaseItemRepo;
	@Autowired
	private ClosingRepository closingRepository;

	private void updateClosing(List<PurchaseItemDto> purchaseItemDtoList) throws ParseException {
		for (PurchaseItemDto purchaseItemDto : purchaseItemDtoList) {
			Closing existingClosing = closingRepository.getClosingByItemId(purchaseItemDto.getItemId());
			if (existingClosing != null) { // update closing;
				PurchaseItem existingPurchaseItem = purchaseItemDto.getId() != null ? purchaseItemRepo.findById(purchaseItemDto.getId()).orElse(null) : null;
				BigDecimal newQty = existingClosing.getQty();
				if(existingPurchaseItem != null) { // purchase item update
					int compare = purchaseItemDto.getQty().compareTo(existingPurchaseItem.getQty());
					if(compare == 1) { // edit purchase item with increase qty
						BigDecimal increaseQty =  purchaseItemDto.getQty().subtract(existingPurchaseItem.getQty());
						newQty = newQty.add(increaseQty);
					} else if (compare == -1) { // edit purchase item with reduce qty
						BigDecimal reduceQty = existingPurchaseItem.getQty().subtract(purchaseItemDto.getQty());
						newQty = newQty.subtract(reduceQty);
					} 
				} else { // purchase item save
					newQty = newQty.add(purchaseItemDto.getQty());
				}
				
				existingClosing.setQty(newQty); // set new qty
				existingClosing.setUpdateDate(new Date()); // set update date
				closingRepository.save(existingClosing);
			} else { // insert closing
				ClosingDto closingDto = new ClosingDto(purchaseItemDto.getItemId(), purchaseItemDto.getQty());
				Closing closing = closingDto.getEntity();
				closingRepository.save(closing);
			}
		}
	}
		
	private void updatePayment(Purchase purchase, BigDecimal payAmount) throws ParseException {
		PurchasePayment existingPayment = purchasePaymentRepo.findByPurchase(purchase);
		if(existingPayment != null) {
			existingPayment.setPayAmount(payAmount); // update pay amount
			existingPayment.updateDate = new Date();
			int payAmountCheck = existingPayment.getPayAmount().compareTo(payAmount); // check pay amount 
			if(payAmountCheck !=0) {
				existingPayment.setPayAmount(payAmount);
			}
			purchasePaymentRepo.save(existingPayment);
		} else {
			PurchasePaymentDto purchasePaymentDto  = new PurchasePaymentDto(purchase.getId(), purchase.getSupplier().getId(),
					purchase.getCreatedDate(), payAmount);
			PurchasePayment purchasePayment = purchasePaymentDto.getEntity();
			purchasePaymentRepo.save(purchasePayment);
		}
	}
	
	@Override
	public GenericResponse createPurchase(PurchaseDto purchaseDto) throws Exception {
		updateClosing(purchaseDto.getPurchaseItemDtoList()); // update closing
		Purchase purchase = purchaseDto.getEntity();
		Purchase saveObj = purchaseRepo.saveAndFlush(purchase);	
		updatePayment(saveObj, purchaseDto.getPayAmount()); // update payment table	
		return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
	}

	@Override
	public GenericResponse deletePurchase(long purchaseId) throws Exception {
		Purchase existingPurchase = purchaseRepo.findById(purchaseId).orElse(null);
		if(existingPurchase != null) {
			List<PurchaseItem> purchaseIteList = purchaseItemRepo.getPurchaseItemByPurchaseId(purchaseId);
			for(PurchaseItem purchaseItem: purchaseIteList) {
				Closing existingClosing = closingRepository.getClosingByItemId(purchaseItem.getItem().getId());
				if(existingClosing != null) {
					existingClosing.setQty(existingClosing.getQty().subtract(purchaseItem.getQty())); // reduce delete qty to closing
					closingRepository.save(existingClosing);
				}		
			}
			
			PurchasePayment purchasePayment =  purchasePaymentRepo.findByPurchase(existingPurchase);
			purchasePaymentRepo.delete(purchasePayment);// delete payment
			purchaseRepo.deleteById(purchaseId);
			return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
		}	
		return new GenericResponse(true, ResponseMessage.DELETE_FAIL);
	}

	@Override
	public GenericResponse savePurchasePayment(PurchasePaymentDto purchasePaymentDto) throws Exception {
		PurchasePayment purchasePayment = purchasePaymentDto.getEntity();
		purchasePaymentRepo.save(purchasePayment);
		return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
	}

	@Override
	public GenericResponse deletePurchasePayment(long purchasePaymentId) {
		purchasePaymentRepo.deleteById(purchasePaymentId);
		return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);

	}

	@Override
	public PurchaseHeaderListDto getPurchaseHeaderViewLazyLoad(PurchaseHeaderListSearch searchModel) {
		PurchaseHeaderListDto purchaseHeaderListDto = new PurchaseHeaderListDto();
		String supplierIds = searchModel.getSupplierId() != null ? String.join(",", searchModel.getSupplierId()) : null;
		String purchaseDate = searchModel.getPurchaseDate();
		;
		int pagePerRow = searchModel.getPagePerRow();
		int pageNumber = searchModel.getPageNumber();
		String sortName = searchModel.getSortName();
		int sortOrder = searchModel.getSortOrder();

		List<PurchaseHeaderStoreOutDto> purchaseHeaderList = purchaseRepo.getPurchaseListHeaderViewLazyLoad(supplierIds,
				purchaseDate, pagePerRow, pageNumber, sortName, sortOrder);
		purchaseHeaderListDto.setPurchaseHeaderList(purchaseHeaderList);
		long[] supplierIdList = purchaseHeaderList.stream().mapToLong(PurchaseHeaderStoreOutDto::getSupplierId)
				.distinct().toArray();
		BigDecimal totalCreditAmount = BigDecimal.ZERO;
		BigDecimal totalPayAmount = BigDecimal.ZERO;
		BigDecimal totalBuyAmount = BigDecimal.ZERO;
		for (Long id : supplierIdList) {
			totalPayAmount = totalPayAmount.add(purchasePaymentRepo.getTotalPayAmount(id));
			totalBuyAmount = totalBuyAmount.add(purchaseRepo.getTotalBuyAmount(id));
		}
		totalCreditAmount = totalBuyAmount.subtract(totalPayAmount);
		purchaseHeaderListDto.setTotalCreditAmount(totalCreditAmount);
		purchaseHeaderListDto.setTotalBuyAmount(totalBuyAmount);
		purchaseHeaderListDto.setTotalPayAmount(totalPayAmount);

		return purchaseHeaderListDto;
	}

	@Override
	public List<PurchasePaymentStoreOutDto> getPaymentListByLazyLoad(PurchaseHeaderListSearch searchModel) {
		List<PurchasePaymentStoreOutDto> purchasePaymentList = new ArrayList<PurchasePaymentStoreOutDto>();
		String supplierIds = searchModel.getSupplierId() != null ? String.join(",", searchModel.getSupplierId()) : null;
		String payDate = searchModel.getPayDate();
		String type = null;
		if (searchModel.getType() != null && searchModel.getType().size() == 1) {
			type = searchModel.getType().get(0);
		}
		String remark = searchModel.getRemark();

		int pagePerRow = searchModel.getPagePerRow();
		int pageNumber = searchModel.getPageNumber();
		String sortName = searchModel.getSortName();
		int sortOrder = searchModel.getSortOrder();

		purchasePaymentList = purchasePaymentRepo.getPurchasePaymentListLazyLoad(supplierIds, payDate, type, remark,
				pagePerRow, pageNumber, sortName, sortOrder);
		return purchasePaymentList;
	}

	@Override
	public List<ClosingDto> getAllClosing() {
		List<Closing> closingList = closingRepository.findAll();
		List<ClosingDto> closingDtoList = new ArrayList<ClosingDto>();
		for(Closing closing :  closingList) {
			ClosingDto closingDto = new ClosingDto(closing);
			closingDtoList.add(closingDto);
		}
		return closingDtoList;
	}

	@Override
	public PurchaseDto getPurchaseById(long purchaseId) {
		Purchase purchase = purchaseRepo.findById(purchaseId).orElse(null);
		if(purchase != null) {
			BigDecimal buyTotal = purchaseRepo.getTotalBuyAmount(purchase.getSupplier().getId());
			BigDecimal payTotal = purchasePaymentRepo.getTotalPayAmount(purchase.getSupplier().getId());
			
			BigDecimal previousCreditAmount = buyTotal.subtract(payTotal);
			PurchasePayment purchasePayment = purchasePaymentRepo.findByPurchase(purchase);
			BigDecimal payAmount = BigDecimal.ZERO;
			if(purchasePayment != null) {// set pay amount from payment table
				payAmount = purchasePayment.getPayAmount();
			}
			PurchaseDto purchaseDto =new PurchaseDto(purchase, previousCreditAmount, payAmount);
			return purchaseDto;
			
		} else {
			return null;
		}
	}

	@Override
	public Closing getClosingByType(Long itemId) {
		// TODO Auto-generated method stub
		return closingRepository.getClosingByItemId(itemId);
	}

	@Override
	public PurchasePaymentDto getPurchasePaymentById(long purchasePaymentId) {
		PurchasePayment purchasePayment = purchasePaymentRepo.findById(purchasePaymentId).orElse(null);
		PurchasePaymentDto purchasePaymentDto = new PurchasePaymentDto(purchasePayment);		
		return purchasePaymentDto;
	}

}
