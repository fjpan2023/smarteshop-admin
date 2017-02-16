package com.smarteshop.service.impl;

import com.smarteshop.service.ProductOptionValueService;
import com.smarteshop.domain.ProductOptionValue;
import com.smarteshop.repository.ProductOptionValueRepository;
import com.smarteshop.repository.search.ProductOptionValueSearchRepository;
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
 * Service Implementation for managing ProductOptionValue.
 */
@Service
@Transactional
public class ProductOptionValueServiceImpl implements ProductOptionValueService{

    private final Logger log = LoggerFactory.getLogger(ProductOptionValueServiceImpl.class);
    
    @Inject
    private ProductOptionValueRepository productOptionValueRepository;

    @Inject
    private ProductOptionValueSearchRepository productOptionValueSearchRepository;

    /**
     * Save a productOptionValue.
     *
     * @param productOptionValue the entity to save
     * @return the persisted entity
     */
    public ProductOptionValue save(ProductOptionValue productOptionValue) {
        log.debug("Request to save ProductOptionValue : {}", productOptionValue);
        ProductOptionValue result = productOptionValueRepository.save(productOptionValue);
        productOptionValueSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the productOptionValues.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ProductOptionValue> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOptionValues");
        Page<ProductOptionValue> result = productOptionValueRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one productOptionValue by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductOptionValue findOne(Long id) {
        log.debug("Request to get ProductOptionValue : {}", id);
        ProductOptionValue productOptionValue = productOptionValueRepository.findOne(id);
        return productOptionValue;
    }

    /**
     *  Delete the  productOptionValue by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductOptionValue : {}", id);
        productOptionValueRepository.delete(id);
        productOptionValueSearchRepository.delete(id);
    }

    /**
     * Search for the productOptionValue corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionValue> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductOptionValues for query {}", query);
        Page<ProductOptionValue> result = productOptionValueSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
