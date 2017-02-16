package com.smarteshop.service.impl;

import com.smarteshop.service.ManufacturerService;
import com.smarteshop.domain.Manufacturer;
import com.smarteshop.repository.ManufacturerRepository;
import com.smarteshop.repository.search.ManufacturerSearchRepository;
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
 * Service Implementation for managing Manufacturer.
 */
@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService{

    private final Logger log = LoggerFactory.getLogger(ManufacturerServiceImpl.class);
    
    @Inject
    private ManufacturerRepository manufacturerRepository;

    @Inject
    private ManufacturerSearchRepository manufacturerSearchRepository;

    /**
     * Save a manufacturer.
     *
     * @param manufacturer the entity to save
     * @return the persisted entity
     */
    public Manufacturer save(Manufacturer manufacturer) {
        log.debug("Request to save Manufacturer : {}", manufacturer);
        Manufacturer result = manufacturerRepository.save(manufacturer);
        manufacturerSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the manufacturers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Manufacturer> findAll(Pageable pageable) {
        log.debug("Request to get all Manufacturers");
        Page<Manufacturer> result = manufacturerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one manufacturer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Manufacturer findOne(Long id) {
        log.debug("Request to get Manufacturer : {}", id);
        Manufacturer manufacturer = manufacturerRepository.findOne(id);
        return manufacturer;
    }

    /**
     *  Delete the  manufacturer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Manufacturer : {}", id);
        manufacturerRepository.delete(id);
        manufacturerSearchRepository.delete(id);
    }

    /**
     * Search for the manufacturer corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Manufacturer> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Manufacturers for query {}", query);
        Page<Manufacturer> result = manufacturerSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
