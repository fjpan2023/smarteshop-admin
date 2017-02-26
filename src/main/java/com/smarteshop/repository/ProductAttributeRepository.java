package com.smarteshop.repository;

import org.springframework.data.jpa.repository.*;

import com.smarteshop.domain.catalog.ProductAttribute;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductAttribute entity.
 */
@SuppressWarnings("unused")
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute,Long> {

}
