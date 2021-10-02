package com.erp.dto.purchase;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PurchaseHeaderListSearch {
	
	private List<String> supplierId;
	private List<String> type;
	private String purchaseDate;
	private String payDate;
	private String remark;
	
	private int pagePerRow;
	private int pageNumber;
	private String sortName;
	private int sortOrder;
	private boolean exportAll;
	
	

}
