package com.smarteshop.repository;

import com.smarteshop.domain.ContactPerson;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContactPerson entity.
 */
@SuppressWarnings("unused")
public interface ContactPersonRepository extends JpaRepository<ContactPerson,Long> {

}
