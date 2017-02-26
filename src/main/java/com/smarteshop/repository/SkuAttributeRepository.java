package com.smarteshop.repository;

import org.springframework.data.jpa.repository.*;

import com.smarteshop.domain.catalog.SkuAttribute;

import java.util.List;

/**
 * Spring Data JPA repository for the SkuAttribute entity.
 */
@SuppressWarnings("unused")
public interface SkuAttributeRepository extends JpaRepository<SkuAttribute,Long> {

}
