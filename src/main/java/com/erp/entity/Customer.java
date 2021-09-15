package com.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4407223471176992500L;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "mobile")
	private String Mobile;
	
	@Column(name = "remark")
	private String remark;
}
