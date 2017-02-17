package com.smarteshop.service;

import com.smarteshop.domain.ProductOptionXref;

import java.util.List;

/**
 * Service Interface for managing ProductOptionXref.
 */
public interface ProductOptionXrefService {

    /**
     * Save a productOptionXref.
     *
     * @param productOptionXref the entity to save
     * @return the persisted entity
     */
    ProductOptionXref save(ProductOptionXref productOptionXref);

    /**
     *  Get all the productOptionXrefs.
     *  
     *  @return the list of entities
     */
    List<ProductOptionXref> findAll();

    /**
     *  Get the "id" productOptionXref.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductOptionXref findOne(Long id);

    /**
     *  Delete the "id" productOptionXref.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productOptionXref corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ProductOptionXref> search(String query);
}
