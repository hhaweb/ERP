package com.erp.repository.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import com.erp.entity.purchase.PurchaseItem;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long>{

}
