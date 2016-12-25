package com.smarteshop.service;

import com.smarteshop.domain.VariantValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing VariantValue.
 */
public interface VariantValueService {

    /**
     * Save a variantValue.
     *
     * @param variantValue the entity to save
     * @return the persisted entity
     */
    VariantValue save(VariantValue variantValue);

    /**
     *  Get all the variantValues.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VariantValue> findAll(Pageable pageable);

    /**
     *  Get the "id" variantValue.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VariantValue findOne(Long id);

    /**
     *  Delete the "id" variantValue.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the variantValue corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VariantValue> search(String query, Pageable pageable);
}
