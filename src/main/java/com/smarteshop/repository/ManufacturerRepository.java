package com.smarteshop.repository;

import com.smarteshop.domain.Manufacturer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Manufacturer entity.
 */
@SuppressWarnings("unused")
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {

}
