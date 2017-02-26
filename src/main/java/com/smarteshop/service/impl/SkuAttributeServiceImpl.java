package com.smarteshop.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarteshop.domain.catalog.SkuAttribute;
import com.smarteshop.repository.SkuAttributeRepository;
import com.smarteshop.service.SkuAttributeService;

/**
 * Service Implementation for managing SkuAttribute.
 */
@Service
@Transactional
public class SkuAttributeServiceImpl implements SkuAttributeService{

    private final Logger log = LoggerFactory.getLogger(SkuAttributeServiceImpl.class);

    @Inject
    private SkuAttributeRepository skuAttributeRepository;


    /**
     * Save a skuAttribute.
     *
     * @param skuAttribute the entity to save
     * @return the persisted entity
     */
    @Override
    public SkuAttribute save(SkuAttribute skuAttribute) {
        log.debug("Request to save SkuAttribute : {}", skuAttribute);
        SkuAttribute result = skuAttributeRepository.save(skuAttribute);
        return result;
    }

    /**
     *  Get all the skuAttributes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkuAttribute : {}", id);
        skuAttributeRepository.delete(id);
    }

    @Override
    public Page<SkuAttribute> search(String query, Pageable pageable) {
      // TODO Auto-generated method stub
      return null;
    }

    /**
     * Search for the skuAttribute corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
}
