package com.smarteshop.service.impl;

import com.smarteshop.service.SkuAttributeService;
import com.smarteshop.domain.SkuAttribute;
import com.smarteshop.repository.SkuAttributeRepository;
import com.smarteshop.repository.search.SkuAttributeSearchRepository;
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
 * Service Implementation for managing SkuAttribute.
 */
@Service
@Transactional
public class SkuAttributeServiceImpl implements SkuAttributeService{

    private final Logger log = LoggerFactory.getLogger(SkuAttributeServiceImpl.class);
    
    @Inject
    private SkuAttributeRepository skuAttributeRepository;

    @Inject
    private SkuAttributeSearchRepository skuAttributeSearchRepository;

    /**
     * Save a skuAttribute.
     *
     * @param skuAttribute the entity to save
     * @return the persisted entity
     */
    public SkuAttribute save(SkuAttribute skuAttribute) {
        log.debug("Request to save SkuAttribute : {}", skuAttribute);
        SkuAttribute result = skuAttributeRepository.save(skuAttribute);
        skuAttributeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the skuAttributes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SkuAttribute> findAll(Pageable pageable) {
        log.debug("Request to get all SkuAttributes");
        Page<SkuAttribute> result = skuAttributeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one skuAttribute by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SkuAttribute findOne(Long id) {
        log.debug("Request to get SkuAttribute : {}", id);
        SkuAttribute skuAttribute = skuAttributeRepository.findOne(id);
        return skuAttribute;
    }

    /**
     *  Delete the  skuAttribute by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SkuAttribute : {}", id);
        skuAttributeRepository.delete(id);
        skuAttributeSearchRepository.delete(id);
    }

    /**
     * Search for the skuAttribute corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SkuAttribute> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SkuAttributes for query {}", query);
        Page<SkuAttribute> result = skuAttributeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
