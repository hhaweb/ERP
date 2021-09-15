package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erp.dto.SelectItem;
import com.erp.entity.Customer;


public interface CommonRepository extends	JpaRepository<Customer, Long>{
	
	@Query(value = "select Id, name from erp.customer group by Id", nativeQuery = true)
	List<SelectItem> getCustomerNameList();
	
	@Query(value = "select Id, name from erp.item group by Id", nativeQuery = true)
	List<SelectItem> getItemNameList();
	
	@Query(value = "select Id, name from erp.supplier group by Id", nativeQuery = true)
	List<SelectItem> getSupplierNameList();

}
