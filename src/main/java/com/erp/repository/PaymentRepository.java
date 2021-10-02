package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.dto.sale.PaymentList;
import com.erp.entity.sale.Payment;
import com.erp.entity.sale.Sale;
@Repository
public interface PaymentRepository extends	JpaRepository<Payment, Long>{

	@Query(value = "call getPaymentListByLazyLoad(?1,?2,?3,?4,?5,?6,?7,?8)", nativeQuery = true)
	List<PaymentList> getPaymentListByLazyLoad(String customerId, String payDate,String type,String remark, int pagePerRow,
			int pageNumber, String sortName, int sortOrder);
	
	Payment findBySale(Sale sale);
	
}
