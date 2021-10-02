package com.erp.repository.purchase;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.dto.purchase.PurchaseHeaderStoreOutDto;
import com.erp.dto.purchase.PurchasePaymentStoreOutDto;
import com.erp.entity.purchase.Purchase;
import com.erp.entity.purchase.PurchasePayment;
@Repository
public interface PurchasePaymentRepository extends JpaRepository<PurchasePayment, Long>{

	@Query(value = "SELECT  IF(count(*) > 0 , SUM(pay_amount), 0) FROM purchase_payment WHERE supplier_id = ?1 ", nativeQuery = true)
	BigDecimal getTotalPayAmount(long supplierId);
	
	@Query(value = "call getPurchasePaymentListByLazyLoad(?1,?2,?3,?4,?5,?6,?7,?8)", nativeQuery = true)
	List<PurchasePaymentStoreOutDto> getPurchasePaymentListLazyLoad(String supplierId, String payDate,String type,String remark,int pagePerRow,
			int pageNumber, String sortName, int sortOrder);
	
	PurchasePayment findByPurchase(Purchase purchase);
	
	
	

}
