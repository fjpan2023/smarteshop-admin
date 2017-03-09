package com.smarteshop.service.impl;

import com.smarteshop.service.OrderItemPriceService;
import com.smarteshop.domain.OrderItemPriceDetail;
import com.smarteshop.repository.OrderItemPriceRepository;
import com.smarteshop.repository.search.OrderItemPriceSearchRepository;
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
 * Service Implementation for managing OrderItemPrice.
 */
@Service
@Transactional
public class OrderItemPriceServiceImpl implements OrderItemPriceService{

    private final Logger log = LoggerFactory.getLogger(OrderItemPriceServiceImpl.class);
    
    @Inject
    private OrderItemPriceRepository orderItemPriceRepository;

    @Inject
    private OrderItemPriceSearchRepository orderItemPriceSearchRepository;

    /**
     * Save a orderItemPrice.
     *
     * @param orderItemPrice the entity to save
     * @return the persisted entity
     */
    public OrderItemPriceDetail save(OrderItemPriceDetail orderItemPrice) {
        log.debug("Request to save OrderItemPrice : {}", orderItemPrice);
        OrderItemPriceDetail result = orderItemPriceRepository.save(orderItemPrice);
        orderItemPriceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the orderItemPrices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<OrderItemPriceDetail> findAll(Pageable pageable) {
        log.debug("Request to get all OrderItemPrices");
        Page<OrderItemPriceDetail> result = orderItemPriceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one orderItemPrice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrderItemPriceDetail findOne(Long id) {
        log.debug("Request to get OrderItemPrice : {}", id);
        OrderItemPriceDetail orderItemPrice = orderItemPriceRepository.findOne(id);
        return orderItemPrice;
    }

    /**
     *  Delete the  orderItemPrice by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderItemPrice : {}", id);
        orderItemPriceRepository.delete(id);
        orderItemPriceSearchRepository.delete(id);
    }

    /**
     * Search for the orderItemPrice corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrderItemPriceDetail> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrderItemPrices for query {}", query);
        Page<OrderItemPriceDetail> result = orderItemPriceSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
