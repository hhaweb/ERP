package com.erp.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.erp.entity.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private BigDecimal sellPrice;
	private BigDecimal buyPrice;
		
	public Item getEntity() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Item item = new Item();
		if(this.id == null) {
			item.setCreatedDate(currentDate);
			item.setUpdateDate(currentDate);
		} else {
			item.setId(this.id);
			item.setCreatedDate(df.parse(this.createdDate));
			item.setUpdateDate(currentDate);			
		}	
		item.setName(this.name);
		item.setSellPrice(this.sellPrice);
		item.setBuyPrice(this.buyPrice);	
		return item;
	}
	
	
}
