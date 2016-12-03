package com.smarteshop.service.impl;

import com.smarteshop.service.CurrencyService;
import com.smarteshop.domain.Currency;
import com.smarteshop.repository.CurrencyRepository;
import com.smarteshop.repository.search.CurrencySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Currency.
 */
@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService{

    private final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    
    @Inject
    private CurrencyRepository currencyRepository;

    @Inject
    private CurrencySearchRepository currencySearchRepository;

    /**
     * Save a currency.
     *
     * @param currency the entity to save
     * @return the persisted entity
     */
    public Currency save(Currency currency) {
        log.debug("Request to save Currency : {}", currency);
        Currency result = currencyRepository.save(currency);
        currencySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the currencies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Currency> findAll(Pageable pageable) {
        log.debug("Request to get all Currencies");
        Page<Currency> result = currencyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one currency by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Currency findOne(Long id) {
        log.debug("Request to get Currency : {}", id);
        Currency currency = currencyRepository.findOne(id);
        return currency;
    }

    /**
     *  Delete the  currency by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Currency : {}", id);
        currencyRepository.delete(id);
        currencySearchRepository.delete(id);
    }

    /**
     * Search for the currency corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Currency> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Currencies for query {}", query);
        Page<Currency> result = currencySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
