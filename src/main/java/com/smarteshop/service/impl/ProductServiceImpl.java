package com.smarteshop.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.smarteshop.common.service.BusinessObjectEntityServiceImpl;
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
public class ProductServiceImpl extends BusinessObjectEntityServiceImpl<Long, Product> implements ProductService{

  private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

  private ProductRepository productRepository;

  @Autowired
  private ProductSearchRepository productSearchRepository;

  @Autowired
  private AttachmentService attachmentService;

  public ProductServiceImpl(ProductRepository productRepository){
    super(productRepository);
    this.productRepository = productRepository;

  }
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
    Predicate predicate = qProduct.name.eq(name);
    return this.productRepository.exists(predicate);
  }

  @Override
  public Page<Product> findRelatedProduct(Long id, Pageable pageable) {
    log.debug("Request to get all Products");
    // Specification spec = null;
    Page<Product> result =null;// productRepository.findAll(predicate, pageable);
    return result;
  }

  @Override
  public Page<Product> findAll(Predicate predicate, Pageable pageable) {
    log.debug("Request to get all Products");
    Page<Product> result = productRepository.findAll(predicate, pageable);
    return result;
  }
  @Override
  public void createSKUsByBatch(Long productId, List<String> productOptionValues) {
    // TODO Auto-generated method stub

  }
@Override
public void generateAdditionalSkusByBatch(Long productId) {
	// TODO Auto-generated method stub
	
}
}
