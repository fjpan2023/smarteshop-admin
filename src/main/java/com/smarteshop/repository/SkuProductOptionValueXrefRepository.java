package com.smarteshop.repository;

import com.smarteshop.domain.SkuProductOptionValueXref;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SkuProductOptionValueXref entity.
 */
@SuppressWarnings("unused")
public interface SkuProductOptionValueXrefRepository extends JpaRepository<SkuProductOptionValueXref,Long> {

}
