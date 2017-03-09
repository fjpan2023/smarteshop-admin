package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.OrderPayment;
import com.smarteshop.service.OrderPaymentService;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing OrderPayment.
 */
@RestController
@RequestMapping("/api")
public class OrderPaymentResource {

    private final Logger log = LoggerFactory.getLogger(OrderPaymentResource.class);
        
    @Inject
    private OrderPaymentService orderPaymentService;

    /**
     * POST  /order-payments : Create a new orderPayment.
     *
     * @param orderPayment the orderPayment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderPayment, or with status 400 (Bad Request) if the orderPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-payments")
    @Timed
    public ResponseEntity<OrderPayment> createOrderPayment(@RequestBody OrderPayment orderPayment) throws URISyntaxException {
        log.debug("REST request to save OrderPayment : {}", orderPayment);
        if (orderPayment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderPayment", "idexists", "A new orderPayment cannot already have an ID")).body(null);
        }
        OrderPayment result = orderPaymentService.save(orderPayment);
        return ResponseEntity.created(new URI("/api/order-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderPayment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-payments : Updates an existing orderPayment.
     *
     * @param orderPayment the orderPayment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderPayment,
     * or with status 400 (Bad Request) if the orderPayment is not valid,
     * or with status 500 (Internal Server Error) if the orderPayment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-payments")
    @Timed
    public ResponseEntity<OrderPayment> updateOrderPayment(@RequestBody OrderPayment orderPayment) throws URISyntaxException {
        log.debug("REST request to update OrderPayment : {}", orderPayment);
        if (orderPayment.getId() == null) {
            return createOrderPayment(orderPayment);
        }
        OrderPayment result = orderPaymentService.save(orderPayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderPayment", orderPayment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-payments : get all the orderPayments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orderPayments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/order-payments")
    @Timed
    public ResponseEntity<List<OrderPayment>> getAllOrderPayments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of OrderPayments");
        Page<OrderPayment> page = orderPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /order-payments/:id : get the "id" orderPayment.
     *
     * @param id the id of the orderPayment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderPayment, or with status 404 (Not Found)
     */
    @GetMapping("/order-payments/{id}")
    @Timed
    public ResponseEntity<OrderPayment> getOrderPayment(@PathVariable Long id) {
        log.debug("REST request to get OrderPayment : {}", id);
        OrderPayment orderPayment = orderPaymentService.findOne(id);
        return Optional.ofNullable(orderPayment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /order-payments/:id : delete the "id" orderPayment.
     *
     * @param id the id of the orderPayment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderPayment(@PathVariable Long id) {
        log.debug("REST request to delete OrderPayment : {}", id);
        orderPaymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderPayment", id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-payments?query=:query : search for the orderPayment corresponding
     * to the query.
     *
     * @param query the query of the orderPayment search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/order-payments")
    @Timed
    public ResponseEntity<List<OrderPayment>> searchOrderPayments(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of OrderPayments for query {}", query);
        Page<OrderPayment> page = orderPaymentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/order-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
