package com.erp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6863627143223967814L;
	public static final String ID = "id";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "Id")
	public Long Id;
	
	@Column(name = "created_date")
	public Date createdDate;
	
	@Column(name = "updated_date")
	public Date updateDate;
	
	
}
