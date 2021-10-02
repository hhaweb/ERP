package com.erp.dto.purchase;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.erp.dto.BaseDto;
import com.erp.entity.Item;
import com.erp.entity.purchase.Closing;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ClosingDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long itemId;
	private String itemName;
	private BigDecimal qty;
	
	@JsonIgnore
	public Closing getEntity() throws ParseException {
		Closing closing = new Closing();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		if(this.id == null) {
			closing.setCreatedDate(currentDate);
			closing.setUpdateDate(currentDate);

		} else {
			closing.setId(this.id);
			closing.setCreatedDate(df.parse(this.createdDate));
			closing.setUpdateDate(currentDate);			
		}
		
		Item item = new Item();
		item.setId(this.itemId);
		closing.setItem(item);
		closing.setQty(this.qty);
		return closing;
		
	}

	public ClosingDto(Closing closing) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.id = closing.Id;
		this.itemId = closing.getItem().getId();
		this.itemName = closing.getItem().getName();
		this.qty = closing.getQty();
		this.createdDate = df.format(closing.getCreatedDate());
		this.updatedDate = df.format(closing.getUpdateDate());
	}

	public ClosingDto(Long itemId, BigDecimal qty) {
		super();
		this.itemId = itemId;
		this.qty = qty;
	}

	
	

	
}
