package com.smarteshop.service;

import com.smarteshop.domain.ProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ProductOption.
 */
public interface ProductOptionService {

    /**
     * Save a productOption.
     *
     * @param productOption the entity to save
     * @return the persisted entity
     */
    ProductOption save(ProductOption productOption);

    /**
     *  Get all the productOptions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductOption> findAll(Pageable pageable);

    /**
     *  Get the "id" productOption.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductOption findOne(Long id);

    /**
     *  Delete the "id" productOption.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productOption corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductOption> search(String query, Pageable pageable);
}
