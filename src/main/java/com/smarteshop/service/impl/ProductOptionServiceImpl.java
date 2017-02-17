package com.smarteshop.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarteshop.domain.ProductOption;
import com.smarteshop.repository.ProductOptionRepository;
import com.smarteshop.service.ProductOptionService;

/**
 * Service Implementation for managing ProductOption.
 */
@Service
@Transactional
public class ProductOptionServiceImpl implements ProductOptionService{

    private final Logger log = LoggerFactory.getLogger(ProductOptionServiceImpl.class);

    @Inject
    private ProductOptionRepository productOptionRepository;


    /**
     * Save a productOption.
     *
     * @param productOption the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductOption save(ProductOption productOption) {
        log.debug("Request to save ProductOption : {}", productOption);
        ProductOption result = productOptionRepository.save(productOption);
        return result;
    }

    /**
     *  Get all the productOptions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOption : {}", id);
        productOptionRepository.delete(id);
    }

    /**
     * Search for the productOption corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductOption> search(String query, Pageable pageable) {
        return null;
    }
}
