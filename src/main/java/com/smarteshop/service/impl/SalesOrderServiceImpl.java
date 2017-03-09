package com.smarteshop.service.impl;

import com.smarteshop.service.SalesOrderService;
import com.smarteshop.domain.SalesOrder;
import com.smarteshop.repository.SalesOrderRepository;
import com.smarteshop.repository.search.SalesOrderSearchRepository;
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
 * Service Implementation for managing SalesOrder.
 */
@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService{

    private final Logger log = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
    
    @Inject
    private SalesOrderRepository salesOrderRepository;

    @Inject
    private SalesOrderSearchRepository salesOrderSearchRepository;

    /**
     * Save a salesOrder.
     *
     * @param salesOrder the entity to save
     * @return the persisted entity
     */
    public SalesOrder save(SalesOrder salesOrder) {
        log.debug("Request to save SalesOrder : {}", salesOrder);
        SalesOrder result = salesOrderRepository.save(salesOrder);
        salesOrderSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the salesOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SalesOrder> findAll(Pageable pageable) {
        log.debug("Request to get all SalesOrders");
        Page<SalesOrder> result = salesOrderRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one salesOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalesOrder findOne(Long id) {
        log.debug("Request to get SalesOrder : {}", id);
        SalesOrder salesOrder = salesOrderRepository.findOne(id);
        return salesOrder;
    }

    /**
     *  Delete the  salesOrder by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesOrder : {}", id);
        salesOrderRepository.delete(id);
        salesOrderSearchRepository.delete(id);
    }

    /**
     * Search for the salesOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SalesOrder> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SalesOrders for query {}", query);
        Page<SalesOrder> result = salesOrderSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
