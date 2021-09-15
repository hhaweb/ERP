package com.erp.dto.sale;

import java.math.BigDecimal;
import java.util.Date;

public interface PaymentList {
	  Long getId();
	  Long getCustomerId();
	  Long getSaleId();
	  String getCustomerName();
	  BigDecimal getPayAmount();
	  Date getPayDate();
	  int getTotalRecord();
	  String getRemark();
}
