package com.erp.service;


import java.util.List;

import com.erp.dto.ItemDto;
import com.erp.entity.Item;
import com.erp.util.dto.GenericResponse;


public interface ItemService {

	public GenericResponse saveItem(ItemDto itemDto);
	public List<Item> getAllItems();
	public GenericResponse deleteItem(Long Id);
}
