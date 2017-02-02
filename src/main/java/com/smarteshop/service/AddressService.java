package com.smarteshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smarteshop.domain.Address;

/**
 * Service Interface for managing Address.
 */
public interface AddressService {

    /**
     * Save a address.
     *
     * @param address the entity to save
     * @return the persisted entity
     */
    Address save(Address address);

    /**
     *  Get all the addresses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Address> findAll(Pageable pageable);

    /**
     *  Get the "id" address.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Address findOne(Long id);

    /**
     *  Delete the "id" address.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the address corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Address> search(String query, Pageable pageable);


    public List<Address> findAllAddresses(String entityName, long entityId);
}
