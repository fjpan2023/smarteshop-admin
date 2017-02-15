package com.smarteshop.service.impl;

import com.smarteshop.service.ProductBundleService;
import com.smarteshop.domain.ProductBundle;
import com.smarteshop.repository.ProductBundleRepository;
import com.smarteshop.repository.search.ProductBundleSearchRepository;
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
 * Service Implementation for managing ProductBundle.
 */
@Service
@Transactional
public class ProductBundleServiceImpl implements ProductBundleService{

    private final Logger log = LoggerFactory.getLogger(ProductBundleServiceImpl.class);
    
    @Inject
    private ProductBundleRepository productBundleRepository;

    @Inject
    private ProductBundleSearchRepository productBundleSearchRepository;

    /**
     * Save a productBundle.
     *
     * @param productBundle the entity to save
     * @return the persisted entity
     */
    public ProductBundle save(ProductBundle productBundle) {
        log.debug("Request to save ProductBundle : {}", productBundle);
        ProductBundle result = productBundleRepository.save(productBundle);
        productBundleSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the productBundles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ProductBundle> findAll(Pageable pageable) {
        log.debug("Request to get all ProductBundles");
        Page<ProductBundle> result = productBundleRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one productBundle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductBundle findOne(Long id) {
        log.debug("Request to get ProductBundle : {}", id);
        ProductBundle productBundle = productBundleRepository.findOne(id);
        return productBundle;
    }

    /**
     *  Delete the  productBundle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductBundle : {}", id);
        productBundleRepository.delete(id);
        productBundleSearchRepository.delete(id);
    }

    /**
     * Search for the productBundle corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductBundle> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductBundles for query {}", query);
        Page<ProductBundle> result = productBundleSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
