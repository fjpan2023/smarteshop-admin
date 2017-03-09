package com.smarteshop.repository;

import com.smarteshop.domain.PersonalMessage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonalMessage entity.
 */
@SuppressWarnings("unused")
public interface PersonalMessageRepository extends JpaRepository<PersonalMessage,Long> {

}
