package com.erp.dto.sale;

import java.io.Serializable;
import java.math.BigDecimal;

import com.erp.dto.BaseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaleItemDto extends BaseDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long itemId;
	private BigDecimal qty;
	private BigDecimal price;
	private BigDecimal total;

}
