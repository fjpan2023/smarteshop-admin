package com.smarteshop.service;

import com.smarteshop.domain.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Manufacturer.
 */
public interface ManufacturerService {

    /**
     * Save a manufacturer.
     *
     * @param manufacturer the entity to save
     * @return the persisted entity
     */
    Manufacturer save(Manufacturer manufacturer);

    /**
     *  Get all the manufacturers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Manufacturer> findAll(Pageable pageable);

    /**
     *  Get the "id" manufacturer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Manufacturer findOne(Long id);

    /**
     *  Delete the "id" manufacturer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the manufacturer corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Manufacturer> search(String query, Pageable pageable);
}
