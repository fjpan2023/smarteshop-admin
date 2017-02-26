package com.smarteshop.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.catalog.RelatedProduct;

/**
 * Spring Data JPA repository for the RelatedProduct entity.
 */
@SuppressWarnings("unused")
public interface RelatedProductRepository extends BusinessObjectRepository<RelatedProduct,Long>,
QueryDslPredicateExecutor<RelatedProduct> {

}
