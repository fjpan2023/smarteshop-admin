package com.smarteshop.repository;

import com.smarteshop.domain.OrderPayment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderPayment entity.
 */
@SuppressWarnings("unused")
public interface OrderPaymentRepository extends JpaRepository<OrderPayment,Long> {

}
