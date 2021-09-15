package com.erp.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class BaseDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long id;
	public String createdDate;
	public String updatedDate;

}
