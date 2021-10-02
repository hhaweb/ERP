package com.erp.service;

import java.math.BigDecimal;
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
import com.erp.entity.purchase.Closing;
import com.erp.entity.purchase.PurchaseItem;
import com.erp.entity.sale.Sale;
import com.erp.entity.sale.SaleItem;
import com.erp.repository.ItemRepository;
import com.erp.repository.SaleItemRepository;
import com.erp.repository.SaleRepository;
import com.erp.repository.purchase.ClosingRepository;
import com.erp.repository.purchase.PurchaseItemRepository;
import com.erp.util.dto.GenericResponse;

@Service
public class ItemServicesImpl implements ItemService {
	@Autowired
	public ItemRepository itemRepo;
	@Autowired
	public SaleRepository saleRepo;
	@Autowired
	public ClosingRepository closingRepo;
	@Autowired
	public PurchaseItemRepository purchaseItemRepo;
	@Autowired
	public SaleItemRepository saleItemRepo;

	@Override
	public GenericResponse saveItem(ItemDto itemDto) {

		try {
			Item item = itemDto.getEntity();
			itemRepo.save(item);
			return new GenericResponse(true, ResponseMessage.SAVE_SUCCESS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new GenericResponse(false, ResponseMessage.INTERNAL_ERROR);
		}

	}

	@Override
	public List<Item> getAllItems(boolean checkClosing) {
		return checkClosing == true ? itemRepo.getAllItemWithClosing() : itemRepo.findAll();
	}

	@Override
	public GenericResponse deleteItem(Long Id) {
		Item existingItem = itemRepo.findById(Id).orElse(null);
		if (existingItem != null) {
			PurchaseItem pItem = purchaseItemRepo.findByItem(existingItem).stream().findFirst().orElse(null);
			if (pItem != null) {
				return new GenericResponse(false, ResponseMessage.DELETE_FAIL);
			}

			SaleItem sItem = saleItemRepo.findByItem(existingItem).stream().findFirst().orElse(null);
			if (sItem != null) {
				return new GenericResponse(false, ResponseMessage.DELETE_FAIL);
			}

			Closing closing = closingRepo.getClosingByItemId(Id);
			if(closing != null) {
				if (closing.getQty().compareTo(BigDecimal.ZERO) == 0) {
					closingRepo.delete(closing);
				} else {
					return new GenericResponse(false, ResponseMessage.DELETE_FAIL);
				}
			}
			itemRepo.deleteById(Id);
		}

		return new GenericResponse(true, ResponseMessage.DELETE_SUCCESS);
	}

}
