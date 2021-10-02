package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.entity.Customer;
import com.erp.entity.Supplier;
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{

	List<Supplier> findByNameIgnoreCaseContaining(String name);

}
