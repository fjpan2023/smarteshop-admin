package com.smarteshop.service;

import com.smarteshop.domain.ProductBundle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ProductBundle.
 */
public interface ProductBundleService {

    /**
     * Save a productBundle.
     *
     * @param productBundle the entity to save
     * @return the persisted entity
     */
    ProductBundle save(ProductBundle productBundle);

    /**
     *  Get all the productBundles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductBundle> findAll(Pageable pageable);

    /**
     *  Get the "id" productBundle.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductBundle findOne(Long id);

    /**
     *  Delete the "id" productBundle.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productBundle corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductBundle> search(String query, Pageable pageable);
}
