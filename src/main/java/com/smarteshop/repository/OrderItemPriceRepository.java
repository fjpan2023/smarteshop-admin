package com.smarteshop.repository;

import com.smarteshop.domain.OrderItemPriceDetail;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderItemPrice entity.
 */
@SuppressWarnings("unused")
public interface OrderItemPriceRepository extends JpaRepository<OrderItemPriceDetail,Long> {

}
