package com.smarteshop.service;

import com.smarteshop.domain.OrderPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing OrderPayment.
 */
public interface OrderPaymentService {

    /**
     * Save a orderPayment.
     *
     * @param orderPayment the entity to save
     * @return the persisted entity
     */
    OrderPayment save(OrderPayment orderPayment);

    /**
     *  Get all the orderPayments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderPayment> findAll(Pageable pageable);

    /**
     *  Get the "id" orderPayment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrderPayment findOne(Long id);

    /**
     *  Delete the "id" orderPayment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderPayment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderPayment> search(String query, Pageable pageable);
}
