package com.smarteshop.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;
import com.smarteshop.domain.Attachment;
import com.smarteshop.domain.Product;
import com.smarteshop.domain.RelatedProduct;

/**
 * Service Interface for managing Product.
 */
public interface ProductService {

    /**
     * Save a product.
     *
     * @param product the entity to save
     * @return the persisted entity
     */
    Product save(Product product);

    /**
     *  Get all the products.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAll(Predicate predicate, Pageable pageable);

    /**
     *  Get the "id" product.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Product findOne(Long id);

    /**
     *  Delete the "id" product.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the product corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Product> search(String query, Pageable pageable);

    void saveImages(Long productId, Collection<Attachment> images);

    boolean exist(String name);
    Page<Product> findRelatedProduct(Long id, Pageable pageable);
    void createSKUsByBatch(Long productId, List<String> productOptionValues);
    void generateAdditionalSkusByBatch(Long productId);
    Page<Product> findAllProductsByCategory(Long id, Pageable pageable);
    Page<Product> findAllProductsByBrand(Long brandId, Pageable pageable);
    List<RelatedProduct> createRelatedProducts(Long productId, Set<Long> relatedIds);
}
