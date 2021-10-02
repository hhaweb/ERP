package com.erp.repository.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.entity.Item;
import com.erp.entity.purchase.PurchaseItem;
@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long>{
		
	@Query(value = "select * from erp.purchase_item where purchase_id=?1", nativeQuery = true)
	List<PurchaseItem> getPurchaseItemByPurchaseId(long purchaseId);
	
	@Query(value = "select * from erp.purchase_item where item_id=?1", nativeQuery = true)
	List<PurchaseItem> getByItemId(Long itemId);
	
	List<PurchaseItem> findByItem(Item item);
	
	

}
