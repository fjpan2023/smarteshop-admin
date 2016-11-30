package com.smarteshop.repository;

import com.smarteshop.domain.RelatedProduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RelatedProduct entity.
 */
@SuppressWarnings("unused")
public interface RelatedProductRepository extends JpaRepository<RelatedProduct,Long> {

}
