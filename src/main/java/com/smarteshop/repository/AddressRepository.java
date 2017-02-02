package com.smarteshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smarteshop.domain.Address;

/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
public interface AddressRepository extends JpaRepository<Address,Long> {

  @Query("select u from Address u where u.entityName= :entityName AND u.entityId= :entityId")
  public List<Address> queryAddresses(@Param("entityName")String entityName, @Param("entityId")long entityId);

}
