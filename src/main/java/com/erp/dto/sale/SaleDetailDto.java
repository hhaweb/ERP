package com.erp.dto.sale;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SaleDetailDto {
	private String customerName; 
	private String phone;
	private String itemName;
	private BigDecimal qty;
	private BigDecimal price;
	private BigDecimal credit;
	private BigDecimal payAmount;
	private BigDecimal total;
	private String remark;
	private Date payDate;

}
