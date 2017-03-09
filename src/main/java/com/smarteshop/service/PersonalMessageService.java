package com.smarteshop.service;

import com.smarteshop.domain.PersonalMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing PersonalMessage.
 */
public interface PersonalMessageService {

    /**
     * Save a personalMessage.
     *
     * @param personalMessage the entity to save
     * @return the persisted entity
     */
    PersonalMessage save(PersonalMessage personalMessage);

    /**
     *  Get all the personalMessages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonalMessage> findAll(Pageable pageable);

    /**
     *  Get the "id" personalMessage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PersonalMessage findOne(Long id);

    /**
     *  Delete the "id" personalMessage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the personalMessage corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PersonalMessage> search(String query, Pageable pageable);
}
