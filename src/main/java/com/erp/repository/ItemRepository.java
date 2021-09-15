package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erp.dto.SelectItem;
import com.erp.entity.Item;

public interface ItemRepository extends	JpaRepository<Item, Long> {
	

}
