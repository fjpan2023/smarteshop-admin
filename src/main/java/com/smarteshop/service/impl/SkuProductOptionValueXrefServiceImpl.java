package com.smarteshop.service.impl;

import com.smarteshop.service.SkuProductOptionValueXrefService;
import com.smarteshop.domain.catalog.SkuProductOptionValueXref;
import com.smarteshop.repository.SkuProductOptionValueXrefRepository;
import com.smarteshop.repository.search.SkuProductOptionValueXrefSearchRepository;
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
 * Service Implementation for managing SkuProductOptionValueXref.
 */
@Service
@Transactional
public class SkuProductOptionValueXrefServiceImpl implements SkuProductOptionValueXrefService{

    private final Logger log = LoggerFactory.getLogger(SkuProductOptionValueXrefServiceImpl.class);
    
    @Inject
    private SkuProductOptionValueXrefRepository skuProductOptionValueXrefRepository;

    @Inject
    private SkuProductOptionValueXrefSearchRepository skuProductOptionValueXrefSearchRepository;

    /**
     * Save a skuProductOptionValueXref.
     *
     * @param skuProductOptionValueXref the entity to save
     * @return the persisted entity
     */
    public SkuProductOptionValueXref save(SkuProductOptionValueXref skuProductOptionValueXref) {
        log.debug("Request to save SkuProductOptionValueXref : {}", skuProductOptionValueXref);
        SkuProductOptionValueXref result = skuProductOptionValueXrefRepository.save(skuProductOptionValueXref);
        skuProductOptionValueXrefSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the skuProductOptionValueXrefs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SkuProductOptionValueXref> findAll(Pageable pageable) {
        log.debug("Request to get all SkuProductOptionValueXrefs");
        Page<SkuProductOptionValueXref> result = skuProductOptionValueXrefRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one skuProductOptionValueXref by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SkuProductOptionValueXref findOne(Long id) {
        log.debug("Request to get SkuProductOptionValueXref : {}", id);
        SkuProductOptionValueXref skuProductOptionValueXref = skuProductOptionValueXrefRepository.findOne(id);
        return skuProductOptionValueXref;
    }

    /**
     *  Delete the  skuProductOptionValueXref by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SkuProductOptionValueXref : {}", id);
        skuProductOptionValueXrefRepository.delete(id);
        skuProductOptionValueXrefSearchRepository.delete(id);
    }

    /**
     * Search for the skuProductOptionValueXref corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SkuProductOptionValueXref> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SkuProductOptionValueXrefs for query {}", query);
        Page<SkuProductOptionValueXref> result = skuProductOptionValueXrefSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
