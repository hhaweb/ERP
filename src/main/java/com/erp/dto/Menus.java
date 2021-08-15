package com.erp.dto;

import java.util.List;

public class Menus {
	public String label;
    public String icon;
    public List<Item> items;
	public Menus() {
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
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
    
    
}
