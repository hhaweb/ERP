package com.erp.dto;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Basic";
	private Long id;
	private String username;
	private List roles;
	private List<Menus> menus;
	
	
	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public JwtResponse(String token, Long id, String username, List roles , List<Menus> menus) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.menus = menus;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List getRoles() {
		return roles;
	}
	public void setRoles(List roles) {
		this.roles = roles;
	}
	public List<Menus> getMenus() {
		return menus;
	}
	public void setMenus(List<Menus> menus) {
		this.menus = menus;
	}

	
	
	
}
