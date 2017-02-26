package com.smarteshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smarteshop.domain.catalog.Category;
import com.smarteshop.service.dto.CategoryCountInfo;

/**
 * Service Interface for managing Category.
 */
public interface CategoryService {

  /**
   * Save a category.
   *
   * @param category the entity to save
   * @return the persisted entity
   */
  Category save(Category category);

  /**
   *  Get all the categories.
   *
   *  @param pageable the pagination information
   *  @return the list of entities
   */
  Page<Category> findAll(Pageable pageable);

  /**
   *  Get the "id" category.
   *
   *  @param id the id of the entity
   *  @return the entity
   */
  Category findOne(Long id);

  /**
   *  Delete the "id" category.
   *
   *  @param id the id of the entity
   */
  void delete(Long id);

  /**
   * Search for the category corresponding to the query.
   *
   *  @param query the query of the search
   *
   *  @param pageable the pagination information
   *  @return the list of entities
   */
  Page<Category> search(String query, Pageable pageable);

  List<CategoryCountInfo> categoryCountInfo();
  List<Category> findAllSubCategories(Long id);
}
