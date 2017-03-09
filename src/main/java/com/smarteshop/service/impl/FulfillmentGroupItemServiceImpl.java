package com.smarteshop.service.impl;

import com.smarteshop.service.FulfillmentGroupItemService;
import com.smarteshop.domain.FulfillmentGroupItem;
import com.smarteshop.repository.FulfillmentGroupItemRepository;
import com.smarteshop.repository.search.FulfillmentGroupItemSearchRepository;
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
 * Service Implementation for managing FulfillmentGroupItem.
 */
@Service
@Transactional
public class FulfillmentGroupItemServiceImpl implements FulfillmentGroupItemService{

    private final Logger log = LoggerFactory.getLogger(FulfillmentGroupItemServiceImpl.class);
    
    @Inject
    private FulfillmentGroupItemRepository fulfillmentGroupItemRepository;

    @Inject
    private FulfillmentGroupItemSearchRepository fulfillmentGroupItemSearchRepository;

    /**
     * Save a fulfillmentGroupItem.
     *
     * @param fulfillmentGroupItem the entity to save
     * @return the persisted entity
     */
    public FulfillmentGroupItem save(FulfillmentGroupItem fulfillmentGroupItem) {
        log.debug("Request to save FulfillmentGroupItem : {}", fulfillmentGroupItem);
        FulfillmentGroupItem result = fulfillmentGroupItemRepository.save(fulfillmentGroupItem);
        fulfillmentGroupItemSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the fulfillmentGroupItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<FulfillmentGroupItem> findAll(Pageable pageable) {
        log.debug("Request to get all FulfillmentGroupItems");
        Page<FulfillmentGroupItem> result = fulfillmentGroupItemRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one fulfillmentGroupItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FulfillmentGroupItem findOne(Long id) {
        log.debug("Request to get FulfillmentGroupItem : {}", id);
        FulfillmentGroupItem fulfillmentGroupItem = fulfillmentGroupItemRepository.findOne(id);
        return fulfillmentGroupItem;
    }

    /**
     *  Delete the  fulfillmentGroupItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FulfillmentGroupItem : {}", id);
        fulfillmentGroupItemRepository.delete(id);
        fulfillmentGroupItemSearchRepository.delete(id);
    }

    /**
     * Search for the fulfillmentGroupItem corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FulfillmentGroupItem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FulfillmentGroupItems for query {}", query);
        Page<FulfillmentGroupItem> result = fulfillmentGroupItemSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
