package com.erp.dto.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.erp.dto.BaseDto;
import com.erp.entity.Customer;
import com.erp.entity.Item;
import com.erp.entity.sale.Sale;
import com.erp.entity.sale.SaleItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter 
@Setter
public class SaleDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8693246121217108535L;
	private Long customerId;
	private Customer customer;
	private List<SaleItemDto> saleItemListDto;
	private String orderDate;
	
	private String remark;
    private Boolean status;
  
    // total amoutn of sale (one sale)
    private BigDecimal creditAmount;
    private BigDecimal payAmount;
    private BigDecimal totalAmount;
    
    // total amount of customer
	private BigDecimal totalCreditAmount;
	private BigDecimal totalPayAmount;
	private BigDecimal totalSaleAmount;
	
	
	
	public SaleDto() {
	}

	@JsonIgnore
	public Sale getEntity() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Sale sale = new Sale();
		
		if(this.id == null) {
			sale.setCreatedDate(currentDate);
			sale.setUpdateDate(currentDate);
		} else {
			sale.setId(this.id);
			sale.setCreatedDate(df.parse(this.createdDate));
			sale.setUpdateDate(currentDate);			
		}		
		
		Customer customer = new Customer();
		customer.setId(this.customerId);
		sale.setCustomer(customer);
		BigDecimal totalAmount = BigDecimal.ZERO;
		List<SaleItem> saleItemList = new ArrayList<SaleItem>();
		for (SaleItemDto saleItemDto : saleItemListDto) {
			totalAmount = totalAmount.add(saleItemDto.getQty().multiply(saleItemDto.getPrice()));
			
			SaleItem saleItem = new SaleItem();
			if(saleItemDto.getId() == null) {
				saleItem.setCreatedDate(currentDate);
				saleItem.setUpdateDate(currentDate);
			} else {
				saleItem.setId(saleItemDto.getId());
				saleItem.setCreatedDate(df.parse(this.createdDate));
				saleItem.setUpdateDate(currentDate);			
			}
			saleItem.setSale(sale);
			
			Item item = new Item();
			item.setId(saleItemDto.getItemId());
			saleItem.setItem(item);
			
			saleItem.setQty(saleItemDto.getQty());
			saleItem.setPrice(saleItemDto.getPrice());
			saleItem.setTotal(saleItem.getPrice().multiply(saleItem.getQty()));
			saleItemList.add(saleItem);
		}
		saleItemList.remove(null);
		sale.setSaleItemList(saleItemList);
		sale.setTotalAmount(totalAmount);
		sale.setOrderDate(df.parse(this.orderDate));
		sale.setUpdateDate(currentDate);
		return sale;

	}

	
	public SaleDto(Sale sale, BigDecimal previousTotalCredit, BigDecimal payAmount) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.id = sale.getId();
		this.customerId = sale.getCustomer().getId();
		this.orderDate = df.format(sale.getOrderDate());
		this.saleItemListDto = new ArrayList<SaleItemDto>();
		for(SaleItem item : sale.getSaleItemList()) {
			SaleItemDto itemDto = new SaleItemDto();
			itemDto.setId(item.getId());
			itemDto.setItemId(item.getItem().getId());
			itemDto.setPrice(item.getPrice());
			itemDto.setQty(item.getQty());
			itemDto.setTotal(item.getPrice().multiply(item.getQty()));
			this.saleItemListDto.add(itemDto);
		}
		
		
		this.totalCreditAmount = previousTotalCredit;
	
		this.createdDate = df.format(sale.getCreatedDate());
		this.updatedDate = df.format(sale.getUpdateDate());
		this.status = sale.getStatus() == 1 ? true : false;
		
		this.totalAmount = sale.getTotalAmount();
		this.payAmount = payAmount;
		this.creditAmount = this.totalAmount.subtract(this.payAmount);
		
		this.customer = sale.getCustomer();
		this.remark = "";
	}


}
