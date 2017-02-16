package com.smarteshop.service.impl;

import com.smarteshop.service.ProductOptionService;
import com.smarteshop.domain.ProductOption;
import com.smarteshop.repository.ProductOptionRepository;
import com.smarteshop.repository.search.ProductOptionSearchRepository;
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
 * Service Implementation for managing ProductOption.
 */
@Service
@Transactional
public class ProductOptionServiceImpl implements ProductOptionService{

    private final Logger log = LoggerFactory.getLogger(ProductOptionServiceImpl.class);
    
    @Inject
    private ProductOptionRepository productOptionRepository;

    @Inject
    private ProductOptionSearchRepository productOptionSearchRepository;

    /**
     * Save a productOption.
     *
     * @param productOption the entity to save
     * @return the persisted entity
     */
    public ProductOption save(ProductOption productOption) {
        log.debug("Request to save ProductOption : {}", productOption);
        ProductOption result = productOptionRepository.save(productOption);
        productOptionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the productOptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ProductOption> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOptions");
        Page<ProductOption> result = productOptionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one productOption by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductOption findOne(Long id) {
        log.debug("Request to get ProductOption : {}", id);
        ProductOption productOption = productOptionRepository.findOne(id);
        return productOption;
    }

    /**
     *  Delete the  productOption by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductOption : {}", id);
        productOptionRepository.delete(id);
        productOptionSearchRepository.delete(id);
    }

    /**
     * Search for the productOption corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductOption> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductOptions for query {}", query);
        Page<ProductOption> result = productOptionSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
