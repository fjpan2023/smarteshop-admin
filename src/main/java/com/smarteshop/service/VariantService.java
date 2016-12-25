package com.smarteshop.service;

import com.smarteshop.domain.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Variant.
 */
public interface VariantService {

    /**
     * Save a variant.
     *
     * @param variant the entity to save
     * @return the persisted entity
     */
    Variant save(Variant variant);

    /**
     *  Get all the variants.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Variant> findAll(Pageable pageable);

    /**
     *  Get the "id" variant.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Variant findOne(Long id);

    /**
     *  Delete the "id" variant.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the variant corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Variant> search(String query, Pageable pageable);
}
