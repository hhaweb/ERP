package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.entity.Customer;

@Repository
public interface CustomerRepository extends	JpaRepository<Customer, Long>{
	
	List<Customer> findByNameIgnoreCaseContaining(String name);

}
