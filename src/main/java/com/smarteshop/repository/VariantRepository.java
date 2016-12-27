package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.Variant;

/**
 * Spring Data JPA repository for the Variant entity.
 */
@SuppressWarnings("unused")
public interface VariantRepository extends JpaRepository<Variant,Long> {

}
