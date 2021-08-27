package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Item;

public interface ItemRepository extends	JpaRepository<Item, Long> {
	
}
