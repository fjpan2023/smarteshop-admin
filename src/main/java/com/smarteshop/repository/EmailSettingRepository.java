package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smarteshop.domain.EmailSetting;
import com.smarteshop.domain.QEmailSetting;

/**
 * Spring Data JPA repository for the EmailSetting entity.
 */
@SuppressWarnings("unused")
public interface EmailSettingRepository extends JpaRepository<EmailSetting,Long>,QueryDslPredicateExecutor<QEmailSetting> {

}
