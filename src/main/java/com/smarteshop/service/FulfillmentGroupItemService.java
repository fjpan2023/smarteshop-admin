package com.smarteshop.service;

import com.smarteshop.domain.FulfillmentGroupItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing FulfillmentGroupItem.
 */
public interface FulfillmentGroupItemService {

    /**
     * Save a fulfillmentGroupItem.
     *
     * @param fulfillmentGroupItem the entity to save
     * @return the persisted entity
     */
    FulfillmentGroupItem save(FulfillmentGroupItem fulfillmentGroupItem);

    /**
     *  Get all the fulfillmentGroupItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FulfillmentGroupItem> findAll(Pageable pageable);

    /**
     *  Get the "id" fulfillmentGroupItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FulfillmentGroupItem findOne(Long id);

    /**
     *  Delete the "id" fulfillmentGroupItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the fulfillmentGroupItem corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FulfillmentGroupItem> search(String query, Pageable pageable);
}
