package com.smarteshop.service.impl;

import com.smarteshop.service.VariantValueService;
import com.smarteshop.domain.VariantValue;
import com.smarteshop.repository.VariantValueRepository;
import com.smarteshop.repository.search.VariantValueSearchRepository;
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
 * Service Implementation for managing VariantValue.
 */
@Service
@Transactional
public class VariantValueServiceImpl implements VariantValueService{

    private final Logger log = LoggerFactory.getLogger(VariantValueServiceImpl.class);
    
    @Inject
    private VariantValueRepository variantValueRepository;

    @Inject
    private VariantValueSearchRepository variantValueSearchRepository;

    /**
     * Save a variantValue.
     *
     * @param variantValue the entity to save
     * @return the persisted entity
     */
    public VariantValue save(VariantValue variantValue) {
        log.debug("Request to save VariantValue : {}", variantValue);
        VariantValue result = variantValueRepository.save(variantValue);
        variantValueSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the variantValues.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<VariantValue> findAll(Pageable pageable) {
        log.debug("Request to get all VariantValues");
        Page<VariantValue> result = variantValueRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one variantValue by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public VariantValue findOne(Long id) {
        log.debug("Request to get VariantValue : {}", id);
        VariantValue variantValue = variantValueRepository.findOne(id);
        return variantValue;
    }

    /**
     *  Delete the  variantValue by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VariantValue : {}", id);
        variantValueRepository.delete(id);
        variantValueSearchRepository.delete(id);
    }

    /**
     * Search for the variantValue corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VariantValue> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VariantValues for query {}", query);
        Page<VariantValue> result = variantValueSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
