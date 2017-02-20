package com.smarteshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smarteshop.domain.Product;
import com.smarteshop.domain.RelatedProduct;

/**
 * Service Interface for managing RelatedProduct.
 */
public interface RelatedProductService {

    /**
     * Save a relatedProduct.
     *
     * @param relatedProduct the entity to save
     * @return the persisted entity
     */
    RelatedProduct save(RelatedProduct relatedProduct);

    /**
     *  Get all the relatedProducts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RelatedProduct> findAll(Pageable pageable);

    /**
     *  Get the "id" relatedProduct.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RelatedProduct findOne(Long id);

    /**
     *  Delete the "id" relatedProduct.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the relatedProduct corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RelatedProduct> search(String query, Pageable pageable);

    List<RelatedProduct> findRelatedProductsByProduct(Product product);
}
