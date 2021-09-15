package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erp.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long>{


}
