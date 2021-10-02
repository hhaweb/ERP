package com.erp.dto.purchase;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface PurchasePaymentStoreOutDto {
	Long getPaymentId();
	Long getSupplierId();
	Long getPurchaseId();
	String getSupplierName();
	Date getPayDate();
	BigDecimal getPayAmount();
	String getRemark();
	Integer getTotalRecord();
}
