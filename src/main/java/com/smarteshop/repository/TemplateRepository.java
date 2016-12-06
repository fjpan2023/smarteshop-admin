package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.QTemplate;
import com.smarteshop.domain.Template;

/**
 * Spring Data JPA repository for the Template entity.
 */
@SuppressWarnings("unused")
public interface TemplateRepository extends JpaRepository<Template,Long>,
QueryDslPredicateExecutor<QTemplate> {

}
