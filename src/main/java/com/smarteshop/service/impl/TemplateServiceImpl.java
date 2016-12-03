package com.smarteshop.service.impl;

import com.smarteshop.service.TemplateService;
import com.smarteshop.domain.Template;
import com.smarteshop.repository.TemplateRepository;
import com.smarteshop.repository.search.TemplateSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Template.
 */
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService{

    private final Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);
    
    @Inject
    private TemplateRepository templateRepository;

    @Inject
    private TemplateSearchRepository templateSearchRepository;

    /**
     * Save a template.
     *
     * @param template the entity to save
     * @return the persisted entity
     */
    public Template save(Template template) {
        log.debug("Request to save Template : {}", template);
        Template result = templateRepository.save(template);
        templateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the templates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Template> findAll(Pageable pageable) {
        log.debug("Request to get all Templates");
        Page<Template> result = templateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one template by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Template findOne(Long id) {
        log.debug("Request to get Template : {}", id);
        Template template = templateRepository.findOne(id);
        return template;
    }

    /**
     *  Delete the  template by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Template : {}", id);
        templateRepository.delete(id);
        templateSearchRepository.delete(id);
    }

    /**
     * Search for the template corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Template> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Templates for query {}", query);
        Page<Template> result = templateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
