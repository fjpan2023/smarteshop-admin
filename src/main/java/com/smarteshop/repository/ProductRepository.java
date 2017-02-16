package com.smarteshop.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.Product;

/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
public interface ProductRepository extends BusinessObjectRepository<Product,Long>,
QueryDslPredicateExecutor<Product> {


}
