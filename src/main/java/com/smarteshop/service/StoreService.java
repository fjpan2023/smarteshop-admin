package com.smarteshop.service;

import com.smarteshop.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Store.
 */
public interface StoreService {

    /**
     * Save a store.
     *
     * @param store the entity to save
     * @return the persisted entity
     */
    Store save(Store store);

    /**
     *  Get all the stores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Store> findAll(Pageable pageable);

    /**
     *  Get the "id" store.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Store findOne(Long id);

    /**
     *  Delete the "id" store.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the store corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Store> search(String query, Pageable pageable);
}
