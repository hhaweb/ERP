package com.erp.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.erp.entity.Customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto extends BaseDto{
	private Long Id;
	private String name;
	private String phone;
	private String mobile;
	private String address;
	private String remark;
	
	public Customer getEntity() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Customer customer = new Customer();
		
		if(this.Id == null) {
			customer.setCreatedDate(currentDate);
			customer.setUpdateDate(currentDate);
		} else {
			customer.setId(this.Id);
			customer.setCreatedDate(df.parse(this.createdDate));
			customer.setUpdateDate(currentDate);
		}
		
		customer.setId(this.Id);
		customer.setName(this.name);
		customer.setAddress(this.address);
		customer.setPhone(this.phone);
		customer.setMobile(this.mobile);
		customer.setRemark(this.remark);
		return customer;		
	}
	


}
