package com.smarteshop.repository;

import com.smarteshop.domain.ProductOptionXref;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductOptionXref entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionXrefRepository extends JpaRepository<ProductOptionXref,Long> {

}
