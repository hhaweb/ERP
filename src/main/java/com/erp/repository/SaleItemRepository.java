package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.entity.Item;
import com.erp.entity.sale.SaleItem;
@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long>{
	
	@Query(value = "select * from erp.sale_item where sale_id=?1", nativeQuery = true)
	List<SaleItem> getSaleItemBySaleId(long saleId);
	
	List<SaleItem> findByItem(Item item);
}
