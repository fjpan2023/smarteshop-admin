package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.EmailSetting;

/**
 * Spring Data JPA repository for the EmailSetting entity.
 */
@SuppressWarnings("unused")
public interface EmailSettingRepository extends JpaRepository<EmailSetting,Long>,
  QueryDslPredicateExecutor<EmailSetting> {

}
