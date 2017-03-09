package com.smarteshop.repository;

import com.smarteshop.domain.SalesOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalesOrder entity.
 */
@SuppressWarnings("unused")
public interface SalesOrderRepository extends JpaRepository<SalesOrder,Long> {

}
