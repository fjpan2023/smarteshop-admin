package com.smarteshop.repository;

import com.smarteshop.domain.ProductOption;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductOption entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {

}
