package com.smarteshop.service.impl;

import com.smarteshop.service.StoreService;
import com.smarteshop.domain.MerchantStore;
import com.smarteshop.repository.StoreRepository;
import com.smarteshop.repository.search.StoreSearchRepository;
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
 * Service Implementation for managing Store.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService{

    private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);
    
    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreSearchRepository storeSearchRepository;

    /**
     * Save a store.
     *
     * @param store the entity to save
     * @return the persisted entity
     */
    public MerchantStore save(MerchantStore store) {
        log.debug("Request to save Store : {}", store);
        MerchantStore result = storeRepository.save(store);
        storeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the stores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<MerchantStore> findAll(Pageable pageable) {
        log.debug("Request to get all Stores");
        Page<MerchantStore> result = storeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one store by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MerchantStore findOne(Long id) {
        log.debug("Request to get Store : {}", id);
        MerchantStore store = storeRepository.findOne(id);
        return store;
    }

    /**
     *  Delete the  store by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.delete(id);
        storeSearchRepository.delete(id);
    }

    /**
     * Search for the store corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MerchantStore> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Stores for query {}", query);
        Page<MerchantStore> result = storeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
