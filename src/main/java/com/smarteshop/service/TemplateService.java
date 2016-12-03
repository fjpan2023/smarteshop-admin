package com.smarteshop.service;

import com.smarteshop.domain.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Template.
 */
public interface TemplateService {

    /**
     * Save a template.
     *
     * @param template the entity to save
     * @return the persisted entity
     */
    Template save(Template template);

    /**
     *  Get all the templates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Template> findAll(Pageable pageable);

    /**
     *  Get the "id" template.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Template findOne(Long id);

    /**
     *  Delete the "id" template.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the template corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Template> search(String query, Pageable pageable);
}
