package com.erp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "Supplier")
@Getter
@Setter
@NoArgsConstructor
public class Supplier extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7252045015570231690L;
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "remark")
	private String remark;
	
	

}
