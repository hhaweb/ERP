package com.erp.dto.sale;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaleListDto {
	
	BigDecimal totalCreditAmount;
	BigDecimal totalPayAmount;
	BigDecimal totalSellAmount;
	List<SaleHeaderList> saleItemList;
}
