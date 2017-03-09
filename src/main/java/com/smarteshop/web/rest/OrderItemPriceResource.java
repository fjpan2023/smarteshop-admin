package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.OrderItemPriceDetail;
import com.smarteshop.service.OrderItemPriceService;
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
 * REST controller for managing OrderItemPrice.
 */
@RestController
@RequestMapping("/api")
public class OrderItemPriceResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemPriceResource.class);
        
    @Inject
    private OrderItemPriceService orderItemPriceService;

    /**
     * POST  /order-item-prices : Create a new orderItemPrice.
     *
     * @param orderItemPrice the orderItemPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderItemPrice, or with status 400 (Bad Request) if the orderItemPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-item-prices")
    @Timed
    public ResponseEntity<OrderItemPriceDetail> createOrderItemPrice(@RequestBody OrderItemPriceDetail orderItemPrice) throws URISyntaxException {
        log.debug("REST request to save OrderItemPrice : {}", orderItemPrice);
        if (orderItemPrice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderItemPrice", "idexists", "A new orderItemPrice cannot already have an ID")).body(null);
        }
        OrderItemPriceDetail result = orderItemPriceService.save(orderItemPrice);
        return ResponseEntity.created(new URI("/api/order-item-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderItemPrice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-item-prices : Updates an existing orderItemPrice.
     *
     * @param orderItemPrice the orderItemPrice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderItemPrice,
     * or with status 400 (Bad Request) if the orderItemPrice is not valid,
     * or with status 500 (Internal Server Error) if the orderItemPrice couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-item-prices")
    @Timed
    public ResponseEntity<OrderItemPriceDetail> updateOrderItemPrice(@RequestBody OrderItemPriceDetail orderItemPrice) throws URISyntaxException {
        log.debug("REST request to update OrderItemPrice : {}", orderItemPrice);
        if (orderItemPrice.getId() == null) {
            return createOrderItemPrice(orderItemPrice);
        }
        OrderItemPriceDetail result = orderItemPriceService.save(orderItemPrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderItemPrice", orderItemPrice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-item-prices : get all the orderItemPrices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orderItemPrices in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/order-item-prices")
    @Timed
    public ResponseEntity<List<OrderItemPriceDetail>> getAllOrderItemPrices(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of OrderItemPrices");
        Page<OrderItemPriceDetail> page = orderItemPriceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-item-prices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /order-item-prices/:id : get the "id" orderItemPrice.
     *
     * @param id the id of the orderItemPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderItemPrice, or with status 404 (Not Found)
     */
    @GetMapping("/order-item-prices/{id}")
    @Timed
    public ResponseEntity<OrderItemPriceDetail> getOrderItemPrice(@PathVariable Long id) {
        log.debug("REST request to get OrderItemPrice : {}", id);
        OrderItemPriceDetail orderItemPrice = orderItemPriceService.findOne(id);
        return Optional.ofNullable(orderItemPrice)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /order-item-prices/:id : delete the "id" orderItemPrice.
     *
     * @param id the id of the orderItemPrice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-item-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderItemPrice(@PathVariable Long id) {
        log.debug("REST request to delete OrderItemPrice : {}", id);
        orderItemPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderItemPrice", id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-item-prices?query=:query : search for the orderItemPrice corresponding
     * to the query.
     *
     * @param query the query of the orderItemPrice search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/order-item-prices")
    @Timed
    public ResponseEntity<List<OrderItemPriceDetail>> searchOrderItemPrices(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of OrderItemPrices for query {}", query);
        Page<OrderItemPriceDetail> page = orderItemPriceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/order-item-prices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
