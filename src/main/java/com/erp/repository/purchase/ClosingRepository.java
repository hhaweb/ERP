package com.erp.repository.purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.entity.purchase.Closing;

@Repository
public interface ClosingRepository extends JpaRepository<Closing, Long>{
	
	@Query(value = "select * from erp.closing where item_id=?1", nativeQuery = true)
	Closing getClosingByItemId(long itemId);
	
	

}
