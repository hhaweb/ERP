package com.erp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "Purchase")
public class Purchase extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8580243056673506451L;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SupplierId")
	private Supplier supplier;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ItemId")
	private Item item;
	
	
	@Column(name = "Qty")
	private int qty;
	
	@Column(name = "Price")
	private Double price;
	
	@Column(name = "TotalPrice")
	private Double totalPrice;
	
	@Column(name = "Debit")
	private Double debit;
	
	@Column(name = "Credit")
	private Double credit;

	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}
	
	
	
	
}
