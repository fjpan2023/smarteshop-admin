package com.smarteshop.service.impl;

import com.smarteshop.service.VariantService;
import com.smarteshop.domain.Variant;
import com.smarteshop.repository.VariantRepository;
import com.smarteshop.repository.search.VariantSearchRepository;
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
 * Service Implementation for managing Variant.
 */
@Service
@Transactional
public class VariantServiceImpl implements VariantService{

    private final Logger log = LoggerFactory.getLogger(VariantServiceImpl.class);
    
    @Inject
    private VariantRepository variantRepository;

   
    /**
     * Save a variant.
     *
     * @param variant the entity to save
     * @return the persisted entity
     */
    public Variant save(Variant variant) {
        log.debug("Request to save Variant : {}", variant);
        Variant result = variantRepository.save(variant);
        return result;
    }

    /**
     *  Get all the variants.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Variant> findAll(Pageable pageable) {
        log.debug("Request to get all Variants");
        Page<Variant> result = variantRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one variant by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Variant findOne(Long id) {
        log.debug("Request to get Variant : {}", id);
        Variant variant = variantRepository.findOne(id);
        return variant;
    }

    /**
     *  Delete the  variant by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Variant : {}", id);
        variantRepository.delete(id);
    }

    /**
     * Search for the variant corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Variant> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Variants for query {}", query);
        return null;
    }
}
