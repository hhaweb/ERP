package com.erp.dto.purchase;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.erp.dto.BaseDto;
import com.erp.dto.sale.PaymentDto;
import com.erp.dto.sale.SaleItemDto;
import com.erp.entity.Supplier;
import com.erp.entity.purchase.Purchase;
import com.erp.entity.purchase.PurchaseItem;
import com.erp.entity.purchase.PurchasePayment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long supplierId;
	private String supplierName;
	private String purchaseDate;
	private BigDecimal buyTotal;
	private BigDecimal payAmount;
	private BigDecimal creditAmount;
	private BigDecimal previousCreditAmount;
	private List<PurchaseItemDto> purchaseItemDtoList;

	@JsonIgnore
	public Purchase getEntity() throws ParseException {
		Purchase purchase = new Purchase();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		if (this.id == null) {
			purchase.setCreatedDate(currentDate);
			purchase.setUpdateDate(currentDate);

		} else {
			purchase.setId(this.id);
			purchase.setCreatedDate(df.parse(this.createdDate));
			purchase.setUpdateDate(currentDate);
		}
		purchase.setPurchaseDate(df.parse(this.purchaseDate));
		List<PurchaseItem> purchaseItemList = new ArrayList<PurchaseItem>();
		BigDecimal buyTotal = BigDecimal.ZERO;
		for (PurchaseItemDto purchaseItemDtoList : purchaseItemDtoList) {
			PurchaseItem purchaseItem = purchaseItemDtoList.getEntity(purchase);
			buyTotal = buyTotal.add(purchaseItem.getTotal());
			purchaseItemList.add(purchaseItem);
		}
		purchase.setBuyTotal(buyTotal);
		purchase.setPurchaseItemList(purchaseItemList);

		Supplier supplier = new Supplier();
		supplier.setId(this.supplierId);
		purchase.setSupplier(supplier);

		return purchase;
	}

	
	
	
	
	public PurchaseDto(Purchase purchase, BigDecimal previousCreditAmount, BigDecimal PayAmount) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		this.id = purchase.getId();
		this.supplierId = purchase.getSupplier().getId();
		this.supplierName = purchase.getSupplier().getName();
		this.purchaseDate = df.format(purchase.getPurchaseDate());
		this.purchaseItemDtoList = new ArrayList<PurchaseItemDto>();
		for(PurchaseItem purchaseItem : purchase.getPurchaseItemList()) {
			PurchaseItemDto purchaseItemDto = new PurchaseItemDto(purchaseItem);
			this.purchaseItemDtoList.add(purchaseItemDto);
		}

		this.buyTotal = purchase.getBuyTotal();
		this.payAmount = PayAmount;
		this.creditAmount = this.buyTotal.subtract(this.payAmount);
		this.previousCreditAmount = previousCreditAmount;
		this.createdDate = df.format(purchase.getCreatedDate());
		this.updatedDate = df.format(purchase.getUpdateDate());
	}

}
