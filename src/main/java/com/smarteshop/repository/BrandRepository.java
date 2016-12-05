package com.smarteshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarteshop.domain.Brand;

/**
 * Spring Data JPA repository for the Brand entity.
 */
@SuppressWarnings("unused")
public interface BrandRepository extends JpaRepository<Brand,Long>{

}
