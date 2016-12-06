package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;

import com.smarteshop.domain.Product;
import com.smarteshop.domain.QProduct;

/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
public interface ProductRepository extends JpaRepository<Product,Long>,
  QuerydslBinderCustomizer<QProduct> {


}
