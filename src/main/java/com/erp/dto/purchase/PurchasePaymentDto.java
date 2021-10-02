package com.erp.dto.purchase;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.erp.dto.BaseDto;
import com.erp.entity.Supplier;
import com.erp.entity.purchase.Purchase;
import com.erp.entity.purchase.PurchasePayment;
import com.erp.entity.sale.Sale;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PurchasePaymentDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long purchaseId;
	private Long supplierId;
	private String supplierName;
	private String payDate;
	private BigDecimal payAmount;
	private String remark;
	
	
	@JsonIgnore
	public PurchasePayment getEntity() throws ParseException {
		PurchasePayment purchasePayment = new PurchasePayment();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		if(this.id == null) {
			purchasePayment.setCreatedDate(currentDate);
			purchasePayment.setUpdateDate(currentDate);
		} else {
			purchasePayment.setId(this.id);
			purchasePayment.setCreatedDate(df.parse(this.createdDate));
			purchasePayment.setUpdateDate(currentDate);			
		}
		purchasePayment.setPayDate(df.parse(this.payDate));
		
		Supplier supplier = new Supplier();
		supplier.setId(this.supplierId);
		purchasePayment.setSupplier(supplier);
		if(this.purchaseId != null) {
			Purchase purchase = new Purchase();
			purchase.setId(this.purchaseId);
			purchasePayment.setPurchase(purchase);
		}
		
		purchasePayment.setPayAmount(this.payAmount);
		purchasePayment.setRemark(this.remark);		
		return purchasePayment;		
	}

	
	
	public PurchasePaymentDto(Long purchaseId, Long supplierId, Date payDate, BigDecimal payAmount) {
		super();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.purchaseId = purchaseId;
		this.supplierId = supplierId;
		this.payDate =   df.format(payDate);
		this.payAmount = payAmount;
	}
	
	public PurchasePaymentDto(PurchasePayment purchasePayment) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.id = purchasePayment.getId();
		this.supplierId = purchasePayment.getSupplier().getId();
		this.supplierName = purchasePayment.getSupplier().getName();
		if(purchasePayment.getPurchase() != null) {
			this.purchaseId = purchasePayment.getPurchase().getId();
		}		
		this.payAmount = purchasePayment.getPayAmount();
		this.createdDate = df.format(purchasePayment.getCreatedDate());
		this.updatedDate = df.format(purchasePayment.getUpdateDate());
		this.payDate = df.format(purchasePayment.getUpdateDate());
		
	}

	
}
