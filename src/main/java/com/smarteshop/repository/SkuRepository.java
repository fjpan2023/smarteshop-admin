package com.smarteshop.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.catalog.Sku;

/**
 * Spring Data JPA repository for the Sku entity.
 */
public interface SkuRepository extends BusinessObjectRepository<Sku,Long>,
  QueryDslPredicateExecutor<Sku> {

}
