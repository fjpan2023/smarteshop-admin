package com.smarteshop.repository;

import com.smarteshop.domain.ProductAttribute;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductAttribute entity.
 */
@SuppressWarnings("unused")
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute,Long> {

}
