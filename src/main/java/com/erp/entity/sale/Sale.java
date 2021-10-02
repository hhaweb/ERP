package com.erp.entity.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.erp.entity.BaseEntity;
import com.erp.entity.Customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Sale")
@Getter
@Setter
@NoArgsConstructor
public class Sale extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7363978828745589918L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "order_date")
	public Date orderDate;

	@Column(name = "status")
	private byte status;

	@Column(name = "remark")
	private String remark;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sale")
	private List<SaleItem> saleItemList;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sale")
	private Payment payment;
}
