package com.erp.entity.purchase;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.erp.entity.BaseEntity;
import com.erp.entity.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "purchase_item")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItem extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="purchase_id")
	private Purchase purchase;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	@Column(name = "qty")
	private BigDecimal qty;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "total")
	private BigDecimal total;
	
	

}
