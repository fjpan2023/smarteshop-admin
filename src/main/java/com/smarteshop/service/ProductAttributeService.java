package com.smarteshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smarteshop.domain.catalog.ProductAttribute;

import java.util.List;

/**
 * Service Interface for managing ProductAttribute.
 */
public interface ProductAttributeService {

    /**
     * Save a productAttribute.
     *
     * @param productAttribute the entity to save
     * @return the persisted entity
     */
    ProductAttribute save(ProductAttribute productAttribute);

    /**
     *  Get all the productAttributes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductAttribute> findAll(Pageable pageable);

    /**
     *  Get the "id" productAttribute.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductAttribute findOne(Long id);

    /**
     *  Delete the "id" productAttribute.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productAttribute corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductAttribute> search(String query, Pageable pageable);
}
