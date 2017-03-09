package com.smarteshop.service;

import com.smarteshop.domain.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ItemOrder.
 */
public interface ItemOrderService {

    /**
     * Save a itemOrder.
     *
     * @param itemOrder the entity to save
     * @return the persisted entity
     */
    OrderItem save(OrderItem itemOrder);

    /**
     *  Get all the itemOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderItem> findAll(Pageable pageable);

    /**
     *  Get the "id" itemOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrderItem findOne(Long id);

    /**
     *  Delete the "id" itemOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the itemOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderItem> search(String query, Pageable pageable);
}
