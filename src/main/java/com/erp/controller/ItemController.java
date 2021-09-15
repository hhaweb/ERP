package com.erp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.config.ResponseMessage;
import com.erp.dto.ItemDto;
import com.erp.entity.Item;
import com.erp.service.ItemService;
import com.erp.util.dto.GenericResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/item")
public class ItemController {
	@Autowired
	ItemService itemServices;
	
	@PostMapping("/item-save")
	public GenericResponse saveItem(@RequestBody ItemDto item) {
		try {
			itemServices.saveItem(item);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);

		}catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}
	}
	
	@GetMapping("/item-list")
	public List<Item> getAllItem() {
		return itemServices.getAllItems();
	}
	
	@GetMapping("/item-delete")
	public GenericResponse deleteItem(@RequestParam("Id") Long Id) {
		try {
			itemServices.deleteItem(Id);
			return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
		}catch(Exception ex) {
			ex.printStackTrace();
			return new GenericResponse(true, ResponseMessage.INTERNAL_ERROR);
		}
	}

}
