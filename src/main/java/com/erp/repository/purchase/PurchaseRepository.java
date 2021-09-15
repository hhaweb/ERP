package com.erp.repository.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import com.erp.entity.purchase.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}
