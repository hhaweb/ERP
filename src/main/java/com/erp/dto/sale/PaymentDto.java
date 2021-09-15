package com.erp.dto.sale;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.erp.dto.BaseDto;
import com.erp.entity.Customer;
import com.erp.entity.sale.Payment;
import com.erp.entity.sale.Sale;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PaymentDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long customerId;
	private Customer customer;
	private String payDate;
	private BigDecimal payAmount;
	private Long saleId;
	private Sale sale;
	private List<PaymentList> paymentList;
	private BigDecimal totalPayAmount;
	private String remark;
	
	@JsonIgnore
	public Payment getEntity() throws ParseException {
		Payment payment = new Payment();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		if(this.id == null) {
			payment.setCreatedDate(currentDate);
			payment.setUpdateDate(currentDate);
			
		} else {
			payment.setId(this.id);
			payment.setCreatedDate(df.parse(this.createdDate));
			payment.setUpdateDate(currentDate);			
		}
		payment.setPayDate(df.parse(this.payDate));
		
		Customer customer = new Customer();
		customer.setId(this.customerId);
		
		if(this.saleId != null) {
			Sale sale = new Sale();
			sale.setId(this.saleId);
			payment.setSale(sale);
		}
		payment.setPayAmount(this.payAmount);
		payment.setCustomer(customer);
		payment.setRemark(this.remark);
		
		return payment;
	}

	
	
	
	public PaymentDto(List<PaymentList> paymentList, BigDecimal totalPayAmount) {
		super();
		this.paymentList = paymentList;
		this.totalPayAmount = totalPayAmount;
	}


	
	
	public PaymentDto() {
		super();
		// TODO Auto-generated constructor stub
	}




	public PaymentDto(Long customerId, String payDate, BigDecimal payAmount, Long saleId) {
		super();
		this.customerId = customerId;
		this.payDate = payDate;
		this.payAmount = payAmount;
		this.saleId = saleId;
	}
	
	

}
