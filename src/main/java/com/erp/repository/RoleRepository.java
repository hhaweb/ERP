package com.erp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.erp.config.ERole;
import com.erp.entity.Role;
import com.erp.entity.User;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findByName(ERole roleUser);
	
}
