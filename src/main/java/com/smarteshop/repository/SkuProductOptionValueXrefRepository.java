package com.smarteshop.repository;

import org.springframework.data.jpa.repository.*;

import com.smarteshop.domain.catalog.SkuProductOptionValueXref;

import java.util.List;

/**
 * Spring Data JPA repository for the SkuProductOptionValueXref entity.
 */
@SuppressWarnings("unused")
public interface SkuProductOptionValueXrefRepository extends JpaRepository<SkuProductOptionValueXref,Long> {

}
