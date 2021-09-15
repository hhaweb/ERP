package com.erp.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.config.ResponseMessage;
import com.erp.dto.ItemDto;
import com.erp.dto.sale.SaleHeaderList;
import com.erp.entity.Customer;
import com.erp.entity.Item;
import com.erp.entity.sale.Sale;
import com.erp.repository.ItemRepository;
import com.erp.repository.SaleRepository;
import com.erp.util.dto.GenericResponse;

@Service
public class ItemServicesImpl implements ItemService {
	@Autowired
	public ItemRepository itemRepo;
	@Autowired
	public SaleRepository saleRepo;

	@Override
	public GenericResponse saveItem(ItemDto itemDto) {
		
		try {		
			Item item = itemDto.getEntity();
			itemRepo.save(item);			
			return new GenericResponse(true,ResponseMessage.SAVE_SUCCESS);	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GenericResponse(false,ResponseMessage.INTERNAL_ERROR);	
		}
		
	}

	@Override
	public List<Item> getAllItems() {
		List<Item> itemList = itemRepo.findAll();
		return itemList;
	}

	@Override
	public GenericResponse deleteItem(Long Id) {
		itemRepo.deleteById(Id);
		return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
	}
	
	
	


}
