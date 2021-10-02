package com.erp.repository;

import java.io.Serializable;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.erp.entity.BaseEntity;

@Repository
public interface GenericRepository<T extends BaseEntity, ID extends Serializable> extends CrudRepository<T, ID> {
//	public abstract T save(T entity);
}
