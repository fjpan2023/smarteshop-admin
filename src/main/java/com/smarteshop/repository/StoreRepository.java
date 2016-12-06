package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.Store;

/**
 * Spring Data JPA repository for the Store entity.
 */
@SuppressWarnings("unused")
public interface StoreRepository extends JpaRepository<Store,Long>,
QueryDslPredicateExecutor<Store> {

}
