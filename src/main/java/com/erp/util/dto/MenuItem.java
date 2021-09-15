package com.erp.util.dto;

import java.util.List;

public class MenuItem {
	public String label;
    public String icon;
    public List<String> routerLink;
    
    
    
	public MenuItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<String> getRouterLink() {
		return routerLink;
	}
	public void setRouterLink(List<String> routerLink) {
		this.routerLink = routerLink;
	}
    
    
}
