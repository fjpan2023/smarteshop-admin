package com.smarteshop.repository;

import com.smarteshop.domain.CustomerGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerGroup entity.
 */
@SuppressWarnings("unused")
public interface CustomerGroupRepository extends JpaRepository<CustomerGroup,Long> {

}
