package com.smarteshop.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.smarteshop.domain.QCategory;
import com.smarteshop.domain.catalog.Category;
import com.smarteshop.repository.CategoryRepository;
import com.smarteshop.repository.search.CategorySearchRepository;
import com.smarteshop.service.CategoryService;
import com.smarteshop.service.dto.CategoryCountInfo;

/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

  private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

  @Inject
  private CategoryRepository categoryRepository;

  @Inject
  private CategorySearchRepository categorySearchRepository;

  /**
   * Save a category.
   *
   * @param category the entity to save
   * @return the persisted entity
   */
  @Override
  public Category save(Category category) {
    log.debug("Request to save Category : {}", category);
    Category result = categoryRepository.save(category);
    categorySearchRepository.save(result);
    return result;
  }

  /**
   *  Get all the categories.
   *
   *  @param pageable the pagination information
   *  @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<Category> findAll(Pageable pageable) {
    log.debug("Request to get all Categories");
    Page<Category> result = categoryRepository.findAll(pageable);
    return result;
  }

  /**
   *  Get one category by id.
   *
   *  @param id the id of the entity
   *  @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public Category findOne(Long id) {
    log.debug("Request to get Category : {}", id);
    Category category = categoryRepository.findOne(id);
    return category;
  }

  /**
   *  Delete the  category by id.
   *
   *  @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Category : {}", id);
    categoryRepository.delete(id);
    categorySearchRepository.delete(id);
  }

  /**
   * Search for the category corresponding to the query.
   *
   *  @param query the query of the search
   *  @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<Category> search(String query, Pageable pageable) {
    log.debug("Request to search for a page of Categories for query {}", query);
    Page<Category> result = categorySearchRepository.search(queryStringQuery(query), pageable);
    return result;
  }

  @Override
  public List<CategoryCountInfo> categoryCountInfo() {
    return  this.categoryRepository.categoryCountInfo();
  }

  @Override
  public List<Category> findAllSubCategories(Long id) {
    QCategory qCategory = QCategory.category;
    Predicate predicate = qCategory.parentId.eq(id);
    return (List<Category>) this.categoryRepository.findAll(predicate);
  }
}
