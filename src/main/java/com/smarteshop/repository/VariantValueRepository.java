package com.smarteshop.repository;

import com.smarteshop.domain.VariantValue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VariantValue entity.
 */
@SuppressWarnings("unused")
public interface VariantValueRepository extends JpaRepository<VariantValue,Long> {

}
