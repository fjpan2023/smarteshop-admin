package com.smarteshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smarteshop.domain.catalog.SkuProductOptionValueXref;

import java.util.List;

/**
 * Service Interface for managing SkuProductOptionValueXref.
 */
public interface SkuProductOptionValueXrefService {

    /**
     * Save a skuProductOptionValueXref.
     *
     * @param skuProductOptionValueXref the entity to save
     * @return the persisted entity
     */
    SkuProductOptionValueXref save(SkuProductOptionValueXref skuProductOptionValueXref);

    /**
     *  Get all the skuProductOptionValueXrefs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SkuProductOptionValueXref> findAll(Pageable pageable);

    /**
     *  Get the "id" skuProductOptionValueXref.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SkuProductOptionValueXref findOne(Long id);

    /**
     *  Delete the "id" skuProductOptionValueXref.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the skuProductOptionValueXref corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SkuProductOptionValueXref> search(String query, Pageable pageable);
}
