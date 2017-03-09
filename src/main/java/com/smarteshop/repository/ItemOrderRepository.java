package com.smarteshop.repository;

import com.smarteshop.domain.OrderItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ItemOrder entity.
 */
@SuppressWarnings("unused")
public interface ItemOrderRepository extends JpaRepository<OrderItem,Long> {

}
