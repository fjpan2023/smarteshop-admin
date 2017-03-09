package com.smarteshop.service.impl;

import com.smarteshop.service.OrderPaymentService;
import com.smarteshop.domain.OrderPayment;
import com.smarteshop.repository.OrderPaymentRepository;
import com.smarteshop.repository.search.OrderPaymentSearchRepository;
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
 * Service Implementation for managing OrderPayment.
 */
@Service
@Transactional
public class OrderPaymentServiceImpl implements OrderPaymentService{

    private final Logger log = LoggerFactory.getLogger(OrderPaymentServiceImpl.class);
    
    @Inject
    private OrderPaymentRepository orderPaymentRepository;

    @Inject
    private OrderPaymentSearchRepository orderPaymentSearchRepository;

    /**
     * Save a orderPayment.
     *
     * @param orderPayment the entity to save
     * @return the persisted entity
     */
    public OrderPayment save(OrderPayment orderPayment) {
        log.debug("Request to save OrderPayment : {}", orderPayment);
        OrderPayment result = orderPaymentRepository.save(orderPayment);
        orderPaymentSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the orderPayments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<OrderPayment> findAll(Pageable pageable) {
        log.debug("Request to get all OrderPayments");
        Page<OrderPayment> result = orderPaymentRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one orderPayment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrderPayment findOne(Long id) {
        log.debug("Request to get OrderPayment : {}", id);
        OrderPayment orderPayment = orderPaymentRepository.findOne(id);
        return orderPayment;
    }

    /**
     *  Delete the  orderPayment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderPayment : {}", id);
        orderPaymentRepository.delete(id);
        orderPaymentSearchRepository.delete(id);
    }

    /**
     * Search for the orderPayment corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrderPayment> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrderPayments for query {}", query);
        Page<OrderPayment> result = orderPaymentSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
