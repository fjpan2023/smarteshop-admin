package com.smarteshop.repository;

import com.smarteshop.domain.ProductBundle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductBundle entity.
 */
@SuppressWarnings("unused")
public interface ProductBundleRepository extends JpaRepository<ProductBundle,Long> {

}
