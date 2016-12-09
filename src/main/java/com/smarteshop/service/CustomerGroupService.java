package com.smarteshop.service;

import com.smarteshop.domain.CustomerGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing CustomerGroup.
 */
public interface CustomerGroupService {

    /**
     * Save a customerGroup.
     *
     * @param customerGroup the entity to save
     * @return the persisted entity
     */
    CustomerGroup save(CustomerGroup customerGroup);

    /**
     *  Get all the customerGroups.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerGroup> findAll(Pageable pageable);

    /**
     *  Get the "id" customerGroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerGroup findOne(Long id);

    /**
     *  Delete the "id" customerGroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the customerGroup corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerGroup> search(String query, Pageable pageable);
}
