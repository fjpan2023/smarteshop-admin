package com.smarteshop.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.smarteshop.domain.Attachment;
import com.smarteshop.domain.Product;
import com.smarteshop.domain.QProduct;
import com.smarteshop.repository.ProductRepository;
import com.smarteshop.repository.search.ProductSearchRepository;
import com.smarteshop.service.AttachmentService;
import com.smarteshop.service.ProductService;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

  private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

  @Inject
  private ProductRepository productRepository;

  @Inject
  private ProductSearchRepository productSearchRepository;

  @Autowired
  private AttachmentService attachmentService;

  /**
   * Save a product.
   *
   * @param product the entity to save
   * @return the persisted entity
   */
  @Override
  public Product save(Product product) {
    log.debug("Request to save Product : {}", product);
    Product result = productRepository.save(product);
    productSearchRepository.save(result);
    return result;
  }

  /**
   *  Get all the products.
   *
   *  @param pageable the pagination information
   *  @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<Product> findAll(Pageable pageable) {
    log.debug("Request to get all Products");
    Page<Product> result = productRepository.findAll(pageable);
    return result;
  }

  /**
   *  Get one product by id.
   *
   *  @param id the id of the entity
   *  @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public Product findOne(Long id) {
    log.debug("Request to get Product : {}", id);
    Product product = productRepository.findOne(id);
    return product;
  }

  /**
   *  Delete the  product by id.
   *
   *  @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Product : {}", id);
    productRepository.delete(id);
    productSearchRepository.delete(id);
  }

  /**
   * Search for the product corresponding to the query.
   *
   *  @param query the query of the search
   *  @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<Product> search(String query, Pageable pageable) {
    log.debug("Request to search for a page of Products for query {}", query);
    Page<Product> result = productSearchRepository.search(queryStringQuery(query), pageable);
    return result;
  }

  @Override
  public void saveImages(Long productId, Collection<Attachment> images) {
    for(Attachment each : images){
      each.setEntityId(productId);
      this.attachmentService.save(each);
    }

  }

  @Override
  public boolean exist(String name, String code) {
    QProduct qProduct = QProduct.product;
    Predicate predicate = qProduct.name.eq(name).or( qProduct.code.eq(code));
    return this.productRepository.exists(predicate);
  }
}
