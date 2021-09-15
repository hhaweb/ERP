package com.erp.dto.sale;

import java.math.BigDecimal;
import java.util.Date;

public interface SaleHeaderList {
	  Long getId();
	  Long getCustomerId();
	  String getCustomerName();
	  BigDecimal getDebit();
	  BigDecimal getCredit();
	  BigDecimal getTotalAmount();
	  Date getOrderDate();
	  int getTotalRecord();
	  
	  BigDecimal getTotalCredit();
	  BigDecimal getTotalDebit();
}
