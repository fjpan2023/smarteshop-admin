package com.smarteshop.service;

import com.smarteshop.domain.ContactPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ContactPerson.
 */
public interface ContactPersonService {

    /**
     * Save a contactPerson.
     *
     * @param contactPerson the entity to save
     * @return the persisted entity
     */
    ContactPerson save(ContactPerson contactPerson);

    /**
     *  Get all the contactPeople.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactPerson> findAll(Pageable pageable);

    /**
     *  Get the "id" contactPerson.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContactPerson findOne(Long id);

    /**
     *  Delete the "id" contactPerson.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contactPerson corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactPerson> search(String query, Pageable pageable);
}
