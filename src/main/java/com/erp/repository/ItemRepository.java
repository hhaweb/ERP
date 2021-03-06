package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.dto.SelectItem;
import com.erp.entity.Item;
import com.erp.entity.sale.SaleItem;
@Repository
public interface ItemRepository extends	JpaRepository<Item, Long> {
	
	
	@Query(value = "select * from item where id in (select item_id from closing where qty > 0)", nativeQuery = true)
	List<Item> getAllItemWithClosing();
}
