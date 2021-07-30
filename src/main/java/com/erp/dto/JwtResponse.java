package com.erp.dto;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Basic";
	private Long id;
	private String username;
	private String password;
	private List roles;
	
	
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
	public JwtResponse(String token, Long id, String username, String password, List roles) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List getRoles() {
		return roles;
	}
	public void setRoles(List roles) {
		this.roles = roles;
	}
}
