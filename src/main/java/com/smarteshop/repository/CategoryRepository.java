package com.smarteshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.Category;
import com.smarteshop.service.dto.CategoryCountInfo;

/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
public interface CategoryRepository extends JpaRepository<Category,Long>,QueryDslPredicateExecutor<Category> {

  @Query("select u.parentId, count(*) from Category u group by u.parentId")
  public List<CategoryCountInfo> categoryCountInfo();

}
