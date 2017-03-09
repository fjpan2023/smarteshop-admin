package com.smarteshop.service;

import com.smarteshop.domain.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing SalesOrder.
 */
public interface SalesOrderService {

    /**
     * Save a salesOrder.
     *
     * @param salesOrder the entity to save
     * @return the persisted entity
     */
    SalesOrder save(SalesOrder salesOrder);

    /**
     *  Get all the salesOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SalesOrder> findAll(Pageable pageable);

    /**
     *  Get the "id" salesOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SalesOrder findOne(Long id);

    /**
     *  Delete the "id" salesOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the salesOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SalesOrder> search(String query, Pageable pageable);
}
