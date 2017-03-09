package com.smarteshop.repository;

import com.smarteshop.domain.FulfillmentGroupItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FulfillmentGroupItem entity.
 */
@SuppressWarnings("unused")
public interface FulfillmentGroupItemRepository extends JpaRepository<FulfillmentGroupItem,Long> {

}
