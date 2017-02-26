package com.smarteshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smarteshop.domain.catalog.Brand;

import java.util.List;

/**
 * Service Interface for managing Brand.
 */
public interface BrandService {

    /**
     * Save a brand.
     *
     * @param brand the entity to save
     * @return the persisted entity
     */
    Brand save(Brand brand);

    /**
     *  Get all the brands.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Brand> findAll(Pageable pageable);

    /**
     *  Get the "id" brand.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Brand findOne(Long id);

    /**
     *  Delete the "id" brand.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the brand corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Brand> search(String query, Pageable pageable);
}
