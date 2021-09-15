package com.erp.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.dto.SelectItem;
import com.erp.dto.sale.PaymentList;
import com.erp.dto.sale.SaleHeaderList;
import com.erp.entity.sale.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "call getSaleHeaderListByLazyLoad(?1,?2,?3,?4,?5,?6)", nativeQuery = true)
	List<SaleHeaderList> getSaleListHeaderViewLazyLoad(String customerId, String orderDate, int pagePerRow,
			int pageNumber, String sortName, int sortOrder);
		
	@Query(value = "SELECT  IF(count(*) > 0 , SUM(pay_amount), 0) FROM payment WHERE customer_id = ?1 ", nativeQuery = true)
	BigDecimal getTotalPayAmount(long customerId);
	
	@Query(value = "SELECT IF(count(*) > 0 , SUM(total_amount), 0) FROM sale WHERE customer_id = ?1 ", nativeQuery = true)
	BigDecimal getTotalSaleAmount(long customerId);
	
	@Query(value = "SELECT * FROM sale as s "
			+ "left join sale_item as item on item.sale_id = s.id "
			+ "left join customer as c on c.id = s.customer_id "
			+ "WHERE s.id = ?1 ", nativeQuery = true)
	Sale getSaleById(Long Id);
	

}
