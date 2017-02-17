package com.smarteshop.service;

import com.smarteshop.domain.SkuAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing SkuAttribute.
 */
public interface SkuAttributeService {

    /**
     * Save a skuAttribute.
     *
     * @param skuAttribute the entity to save
     * @return the persisted entity
     */
    SkuAttribute save(SkuAttribute skuAttribute);

    /**
     *  Get all the skuAttributes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SkuAttribute> findAll(Pageable pageable);

    /**
     *  Get the "id" skuAttribute.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SkuAttribute findOne(Long id);

    /**
     *  Delete the "id" skuAttribute.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the skuAttribute corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SkuAttribute> search(String query, Pageable pageable);
}
