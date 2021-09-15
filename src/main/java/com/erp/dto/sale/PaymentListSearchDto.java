package com.erp.dto.sale;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentListSearchDto {
	private List<String> customerId;
	private List<String> type;
	private String payDate;
	private int pagePerRow;
	private int pageNumber;
	private String sortName;
	private int sortOrder;
	private boolean exportAll;
	private String remark;
}
