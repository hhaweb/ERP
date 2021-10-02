package com.erp.entity.purchase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.erp.entity.BaseEntity;
import com.erp.entity.Supplier;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "purchase_payment")
@Getter
@Setter
@NoArgsConstructor
public class PurchasePayment extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;
	
	@Column(name = "pay_amount")
	private BigDecimal payAmount;
	
	@Column(name = "pay_date")
	private Date payDate;
	
	@Column(name = "remark")
	private String remark;
}
