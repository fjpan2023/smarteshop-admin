package com.smarteshop.service;

import com.smarteshop.domain.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Currency.
 */
public interface CurrencyService {

    /**
     * Save a currency.
     *
     * @param currency the entity to save
     * @return the persisted entity
     */
    Currency save(Currency currency);

    /**
     *  Get all the currencies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Currency> findAll(Pageable pageable);

    /**
     *  Get the "id" currency.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Currency findOne(Long id);

    /**
     *  Delete the "id" currency.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the currency corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Currency> search(String query, Pageable pageable);
}
