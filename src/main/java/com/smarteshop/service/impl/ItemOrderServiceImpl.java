package com.smarteshop.service.impl;

import com.smarteshop.service.ItemOrderService;
import com.smarteshop.domain.OrderItem;
import com.smarteshop.repository.ItemOrderRepository;
import com.smarteshop.repository.search.ItemOrderSearchRepository;
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
 * Service Implementation for managing ItemOrder.
 */
@Service
@Transactional
public class ItemOrderServiceImpl implements ItemOrderService{

    private final Logger log = LoggerFactory.getLogger(ItemOrderServiceImpl.class);
    
    @Inject
    private ItemOrderRepository itemOrderRepository;

    @Inject
    private ItemOrderSearchRepository itemOrderSearchRepository;

    /**
     * Save a itemOrder.
     *
     * @param itemOrder the entity to save
     * @return the persisted entity
     */
    public OrderItem save(OrderItem itemOrder) {
        log.debug("Request to save ItemOrder : {}", itemOrder);
        OrderItem result = itemOrderRepository.save(itemOrder);
        itemOrderSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the itemOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<OrderItem> findAll(Pageable pageable) {
        log.debug("Request to get all ItemOrders");
        Page<OrderItem> result = itemOrderRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one itemOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrderItem findOne(Long id) {
        log.debug("Request to get ItemOrder : {}", id);
        OrderItem itemOrder = itemOrderRepository.findOne(id);
        return itemOrder;
    }

    /**
     *  Delete the  itemOrder by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemOrder : {}", id);
        itemOrderRepository.delete(id);
        itemOrderSearchRepository.delete(id);
    }

    /**
     * Search for the itemOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrderItem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemOrders for query {}", query);
        Page<OrderItem> result = itemOrderSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
