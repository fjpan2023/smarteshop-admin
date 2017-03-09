package com.smarteshop.service;

import com.smarteshop.domain.OrderItemPriceDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing OrderItemPrice.
 */
public interface OrderItemPriceService {

    /**
     * Save a orderItemPrice.
     *
     * @param orderItemPrice the entity to save
     * @return the persisted entity
     */
    OrderItemPriceDetail save(OrderItemPriceDetail orderItemPrice);

    /**
     *  Get all the orderItemPrices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderItemPriceDetail> findAll(Pageable pageable);

    /**
     *  Get the "id" orderItemPrice.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrderItemPriceDetail findOne(Long id);

    /**
     *  Delete the "id" orderItemPrice.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderItemPrice corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OrderItemPriceDetail> search(String query, Pageable pageable);
}
