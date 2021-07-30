package com.erp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.erp.dao.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
