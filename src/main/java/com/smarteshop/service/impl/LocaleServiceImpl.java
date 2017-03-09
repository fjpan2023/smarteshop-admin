package com.smarteshop.service.impl;

import com.smarteshop.service.LocaleService;
import com.smarteshop.domain.Locale;
import com.smarteshop.repository.LocaleRepository;
import com.smarteshop.repository.search.LocaleSearchRepository;
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
 * Service Implementation for managing Locale.
 */
@Service
@Transactional
public class LocaleServiceImpl implements LocaleService{

    private final Logger log = LoggerFactory.getLogger(LocaleServiceImpl.class);
    
    @Inject
    private LocaleRepository localeRepository;

    @Inject
    private LocaleSearchRepository localeSearchRepository;

    /**
     * Save a locale.
     *
     * @param locale the entity to save
     * @return the persisted entity
     */
    public Locale save(Locale locale) {
        log.debug("Request to save Locale : {}", locale);
        Locale result = localeRepository.save(locale);
        localeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the locales.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Locale> findAll(Pageable pageable) {
        log.debug("Request to get all Locales");
        Page<Locale> result = localeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one locale by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Locale findOne(Long id) {
        log.debug("Request to get Locale : {}", id);
        Locale locale = localeRepository.findOne(id);
        return locale;
    }

    /**
     *  Delete the  locale by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Locale : {}", id);
        localeRepository.delete(id);
        localeSearchRepository.delete(id);
    }

    /**
     * Search for the locale corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Locale> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Locales for query {}", query);
        Page<Locale> result = localeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
