package com.erp.dto.purchase;

import java.math.BigDecimal;
import java.util.Date;

public interface PurchaseHeaderStoreOutDto {
	Long getPurchaseId();
	Long getSupplierId();
	String getSupplierName();
	Date getPurchaseDate();
	BigDecimal getBuyTotal();
	BigDecimal getPayAmount();
	BigDecimal getCreditAmount();
	Integer getTotalRecord();
}
