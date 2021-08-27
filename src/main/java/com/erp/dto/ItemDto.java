package com.erp.dto;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import com.erp.entity.Item;

public class ItemDto {

	private Long Id;
	private String name;
	private String createdDate;
	private String updatedDate;
	
	
	
	public ItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Item getEntity() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Item item = new Item();
		if(this.Id == null) {
			item.setCreatedDate(currentDate);
			item.setUpdateDate(currentDate);
		} else {
			item.setId(this.Id);
			item.setCreatedDate(df.parse(this.createdDate));
			item.setUpdateDate(currentDate);
		}	
		item.setName(this.name);	
		return item;
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	
	
}
