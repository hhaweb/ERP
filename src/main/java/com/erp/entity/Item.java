package com.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "Item")
@Getter
@Setter
@NoArgsConstructor
public class Item extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3539683397982427909L;
	
	@Column(name = "name")
	private String name;
	@Column(name = "sell_price")
	private BigDecimal sellPrice;
	@Column(name = "buy_price")
	private BigDecimal buyPrice;

}
