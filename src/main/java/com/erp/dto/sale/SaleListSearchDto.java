package com.erp.dto.sale;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaleListSearchDto {
	
	private List<String> customerId;
	private String orderDate;
	private int pagePerRow;
	private int pageNumber;
	private String sortName;
	private int sortOrder;
	private String type;
}
