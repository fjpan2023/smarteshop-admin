package com.smarteshop.repository;

import com.smarteshop.domain.ProductOptionValue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductOptionValue entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue,Long> {

}
