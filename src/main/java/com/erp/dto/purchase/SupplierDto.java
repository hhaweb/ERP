package com.erp.dto.purchase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.erp.dto.BaseDto;
import com.erp.entity.Supplier;

import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Setter
@NoArgsConstructor
public class SupplierDto extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long Id;
	private String name;
	private String phone;
	private String mobile;
	private String address;
	private String remark;
	
	public Supplier getEntity() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Supplier supplier = new Supplier();
		
		if(this.Id == null) {
			supplier.setCreatedDate(currentDate);
			supplier.setUpdateDate(currentDate);
		} else {
			supplier.setId(this.Id);
			supplier.setCreatedDate(df.parse(this.createdDate));
			supplier.setUpdateDate(currentDate);
		}
		
		supplier.setId(this.Id);
		supplier.setName(this.name);
		supplier.setAddress(this.address);
		supplier.setPhone(this.phone);
		supplier.setMobile(this.mobile);
		supplier.setRemark(this.remark);
		return supplier;		
	}
}
