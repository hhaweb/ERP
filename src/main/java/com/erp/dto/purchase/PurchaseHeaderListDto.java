package com.erp.dto.purchase;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PurchaseHeaderListDto {
	
	private BigDecimal totalPayAmount;
	private BigDecimal totalCreditAmount;
	private BigDecimal totalBuyAmount;
	List<PurchaseHeaderStoreOutDto> purchaseHeaderList;

}
