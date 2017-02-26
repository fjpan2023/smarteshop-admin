package com.smarteshop.repository;

import org.springframework.data.jpa.repository.*;

import com.smarteshop.domain.catalog.ProductOptionValue;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductOptionValue entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue,Long> {

}
