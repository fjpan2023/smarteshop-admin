package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.Comments;
import com.smarteshop.domain.QComments;

/**
 * Spring Data JPA repository for the Comments entity.
 */
@SuppressWarnings("unused")
public interface CommentsRepository extends JpaRepository<Comments,Long>,
QueryDslPredicateExecutor<QComments> {

}
