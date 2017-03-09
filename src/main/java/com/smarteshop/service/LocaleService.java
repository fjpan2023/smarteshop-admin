package com.smarteshop.service;

import com.smarteshop.domain.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Locale.
 */
public interface LocaleService {

    /**
     * Save a locale.
     *
     * @param locale the entity to save
     * @return the persisted entity
     */
    Locale save(Locale locale);

    /**
     *  Get all the locales.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Locale> findAll(Pageable pageable);

    /**
     *  Get the "id" locale.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Locale findOne(Long id);

    /**
     *  Delete the "id" locale.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the locale corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Locale> search(String query, Pageable pageable);
}
