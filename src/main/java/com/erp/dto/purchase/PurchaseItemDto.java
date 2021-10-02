package com.erp.dto.purchase;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.erp.dto.BaseDto;
import com.erp.dto.ItemDto;
import com.erp.entity.Item;
import com.erp.entity.purchase.Purchase;
import com.erp.entity.purchase.PurchaseItem;
import com.erp.entity.sale.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItemDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long purchaseId;
	private Long itemId;
	private String itemName;
	private BigDecimal qty;
	private BigDecimal price;
	private BigDecimal total;
	
	@JsonIgnore
	public PurchaseItem getEntity(Purchase purchase) throws ParseException {
		PurchaseItem purchaseItem = new PurchaseItem();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		if(this.id == null) {
			purchaseItem.setCreatedDate(currentDate);
			purchaseItem.setUpdateDate(currentDate);
			
		} else {
			purchaseItem.setId(this.id);
			purchaseItem.setCreatedDate(df.parse(this.createdDate));
			purchaseItem.setUpdateDate(currentDate);			
		}
		
		Item item = new Item();
		item.setId(this.itemId);
		purchaseItem.setItem(item);
		purchaseItem.setPurchase(purchase);
		
		purchaseItem.setQty(this.qty);
		purchaseItem.setPrice(this.price);
		purchaseItem.setTotal(this.qty.multiply(this.price));
		
		return purchaseItem;
		
	}

	public PurchaseItemDto(PurchaseItem purchaseItem) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.id = purchaseItem.getId();
		this.purchaseId = purchaseItem.getPurchase().getId();
		this.itemId = purchaseItem.getItem().getId();
		this.itemName = purchaseItem.getItem().getName();
		this.qty = purchaseItem.getQty();
		this.price = purchaseItem.getPrice();
		this.total = purchaseItem.getTotal();
		this.createdDate = df.format(purchaseItem.getCreatedDate());
		this.updatedDate = df.format(purchaseItem.getUpdateDate());
		
	}
	
	

}
