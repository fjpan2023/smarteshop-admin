package com.smarteshop.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.smarteshop.domain.Product;
import com.smarteshop.domain.QRelatedProduct;
import com.smarteshop.domain.RelatedProduct;
import com.smarteshop.repository.RelatedProductRepository;
import com.smarteshop.repository.search.RelatedProductSearchRepository;
import com.smarteshop.service.RelatedProductService;

/**
 * Service Implementation for managing RelatedProduct.
 */
@Service
@Transactional
public class RelatedProductServiceImpl implements RelatedProductService{

  private final Logger log = LoggerFactory.getLogger(RelatedProductServiceImpl.class);

  @Inject
  private RelatedProductRepository relatedProductRepository;

  @Inject
  private RelatedProductSearchRepository relatedProductSearchRepository;

  /**
   * Save a relatedProduct.
   *
   * @param relatedProduct the entity to save
   * @return the persisted entity
   */
  @Override
  public RelatedProduct save(RelatedProduct relatedProduct) {
    log.debug("Request to save RelatedProduct : {}", relatedProduct);
    RelatedProduct result = relatedProductRepository.save(relatedProduct);
    relatedProductSearchRepository.save(result);
    return result;
  }

  /**
   *  Get all the relatedProducts.
   *
   *  @param pageable the pagination information
   *  @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<RelatedProduct> findAll(Pageable pageable) {
    log.debug("Request to get all RelatedProducts");
    Page<RelatedProduct> result = relatedProductRepository.findAll(pageable);
    return result;
  }

  /**
   *  Get one relatedProduct by id.
   *
   *  @param id the id of the entity
   *  @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public RelatedProduct findOne(Long id) {
    log.debug("Request to get RelatedProduct : {}", id);
    RelatedProduct relatedProduct = relatedProductRepository.findOne(id);
    return relatedProduct;
  }

  /**
   *  Delete the  relatedProduct by id.
   *
   *  @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete RelatedProduct : {}", id);
    relatedProductRepository.delete(id);
    relatedProductSearchRepository.delete(id);
  }

  /**
   * Search for the relatedProduct corresponding to the query.
   *
   *  @param query the query of the search
   *  @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<RelatedProduct> search(String query, Pageable pageable) {
    log.debug("Request to search for a page of RelatedProducts for query {}", query);
    Page<RelatedProduct> result = relatedProductSearchRepository.search(queryStringQuery(query), pageable);
    return result;
  }

  @Override
  public Page<RelatedProduct> findRelatedProductsByProduct(Product product, Pageable pageable) {
    QRelatedProduct qRelatedProduct = QRelatedProduct.relatedProduct;
    Predicate predicate= qRelatedProduct.product.eq(product);
    return this.relatedProductRepository.findAll(predicate, pageable);
  }
}
