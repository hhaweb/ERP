package com.erp.entity.purchase;

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
import javax.persistence.Table;

import com.erp.entity.BaseEntity;
import com.erp.entity.Supplier;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "purchase")
@Getter
@Setter
@NoArgsConstructor
public class Purchase extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8580243056673506451L;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="supplier_id")
	private Supplier supplier;
	
	@Column(name = "purchase_date")
	private Date purchaseDate;
		
	@Column(name = "buy_total")
	private BigDecimal  buyTotal;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "purchase")
	private List<PurchaseItem> purchaseList;
		
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "purchase")
	private List<PurchasePayment> purchasePaymentList;
}
