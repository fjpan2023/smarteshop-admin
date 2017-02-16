package com.smarteshop.service;

import com.smarteshop.domain.ProductOptionValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ProductOptionValue.
 */
public interface ProductOptionValueService {

    /**
     * Save a productOptionValue.
     *
     * @param productOptionValue the entity to save
     * @return the persisted entity
     */
    ProductOptionValue save(ProductOptionValue productOptionValue);

    /**
     *  Get all the productOptionValues.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductOptionValue> findAll(Pageable pageable);

    /**
     *  Get the "id" productOptionValue.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductOptionValue findOne(Long id);

    /**
     *  Delete the "id" productOptionValue.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productOptionValue corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductOptionValue> search(String query, Pageable pageable);
}
