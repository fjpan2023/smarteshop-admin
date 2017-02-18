package com.smarteshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smarteshop.domain.Product;
import com.smarteshop.domain.Sku;

/**
 * Service Interface for managing Sku.
 */
public interface SkuService {

    /**
     * Save a sku.
     *
     * @param sku the entity to save
     * @return the persisted entity
     */
    Sku save(Sku sku);

    /**
     *  Get all the skus.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Sku> findAll(Pageable pageable);

    /**
     *  Get the "id" sku.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Sku findOne(Long id);

    /**
     *  Delete the "id" sku.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sku corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Sku> search(String query, Pageable pageable);
    List<Sku> findSkusByProduct(Product product);
}
