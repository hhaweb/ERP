package com.erp.repository.purchase;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.dto.purchase.PurchaseHeaderStoreOutDto;
import com.erp.entity.purchase.Purchase;
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	
	@Query(value = "call getPurchaseHeaderListByLazyLoad(?1,?2,?3,?4,?5,?6)", nativeQuery = true)
	List<PurchaseHeaderStoreOutDto> getPurchaseListHeaderViewLazyLoad(String supplierId, String purchaseDate, int pagePerRow,
			int pageNumber, String sortName, int sortOrder);
	
	@Query(value = "SELECT  IF(count(*) > 0 , SUM(buy_total), 0) FROM purchase WHERE supplier_id = ?1 ", nativeQuery = true)
	BigDecimal getTotalBuyAmount(long supplierId);

	
}
