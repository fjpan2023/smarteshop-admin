package com.smarteshop.service.impl;

import com.smarteshop.service.ProductOptionXrefService;
import com.smarteshop.domain.ProductOptionXref;
import com.smarteshop.repository.ProductOptionXrefRepository;
import com.smarteshop.repository.search.ProductOptionXrefSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductOptionXref.
 */
@Service
@Transactional
public class ProductOptionXrefServiceImpl implements ProductOptionXrefService{

    private final Logger log = LoggerFactory.getLogger(ProductOptionXrefServiceImpl.class);
    
    @Inject
    private ProductOptionXrefRepository productOptionXrefRepository;

    @Inject
    private ProductOptionXrefSearchRepository productOptionXrefSearchRepository;

    /**
     * Save a productOptionXref.
     *
     * @param productOptionXref the entity to save
     * @return the persisted entity
     */
    public ProductOptionXref save(ProductOptionXref productOptionXref) {
        log.debug("Request to save ProductOptionXref : {}", productOptionXref);
        ProductOptionXref result = productOptionXrefRepository.save(productOptionXref);
        productOptionXrefSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the productOptionXrefs.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ProductOptionXref> findAll() {
        log.debug("Request to get all ProductOptionXrefs");
        List<ProductOptionXref> result = productOptionXrefRepository.findAll();

        return result;
    }

    /**
     *  Get one productOptionXref by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductOptionXref findOne(Long id) {
        log.debug("Request to get ProductOptionXref : {}", id);
        ProductOptionXref productOptionXref = productOptionXrefRepository.findOne(id);
        return productOptionXref;
    }

    /**
     *  Delete the  productOptionXref by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductOptionXref : {}", id);
        productOptionXrefRepository.delete(id);
        productOptionXrefSearchRepository.delete(id);
    }

    /**
     * Search for the productOptionXref corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProductOptionXref> search(String query) {
        log.debug("Request to search ProductOptionXrefs for query {}", query);
        return StreamSupport
            .stream(productOptionXrefSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
