package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.OrderItem;
import com.smarteshop.service.ItemOrderService;
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
 * REST controller for managing ItemOrder.
 */
@RestController
@RequestMapping("/api")
public class ItemOrderResource {

    private final Logger log = LoggerFactory.getLogger(ItemOrderResource.class);
        
    @Inject
    private ItemOrderService itemOrderService;

    /**
     * POST  /item-orders : Create a new itemOrder.
     *
     * @param itemOrder the itemOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemOrder, or with status 400 (Bad Request) if the itemOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-orders")
    @Timed
    public ResponseEntity<OrderItem> createItemOrder(@RequestBody OrderItem itemOrder) throws URISyntaxException {
        log.debug("REST request to save ItemOrder : {}", itemOrder);
        if (itemOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("itemOrder", "idexists", "A new itemOrder cannot already have an ID")).body(null);
        }
        OrderItem result = itemOrderService.save(itemOrder);
        return ResponseEntity.created(new URI("/api/item-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("itemOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-orders : Updates an existing itemOrder.
     *
     * @param itemOrder the itemOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemOrder,
     * or with status 400 (Bad Request) if the itemOrder is not valid,
     * or with status 500 (Internal Server Error) if the itemOrder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-orders")
    @Timed
    public ResponseEntity<OrderItem> updateItemOrder(@RequestBody OrderItem itemOrder) throws URISyntaxException {
        log.debug("REST request to update ItemOrder : {}", itemOrder);
        if (itemOrder.getId() == null) {
            return createItemOrder(itemOrder);
        }
        OrderItem result = itemOrderService.save(itemOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("itemOrder", itemOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-orders : get all the itemOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of itemOrders in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/item-orders")
    @Timed
    public ResponseEntity<List<OrderItem>> getAllItemOrders(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ItemOrders");
        Page<OrderItem> page = itemOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /item-orders/:id : get the "id" itemOrder.
     *
     * @param id the id of the itemOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemOrder, or with status 404 (Not Found)
     */
    @GetMapping("/item-orders/{id}")
    @Timed
    public ResponseEntity<OrderItem> getItemOrder(@PathVariable Long id) {
        log.debug("REST request to get ItemOrder : {}", id);
        OrderItem itemOrder = itemOrderService.findOne(id);
        return Optional.ofNullable(itemOrder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /item-orders/:id : delete the "id" itemOrder.
     *
     * @param id the id of the itemOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemOrder(@PathVariable Long id) {
        log.debug("REST request to delete ItemOrder : {}", id);
        itemOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("itemOrder", id.toString())).build();
    }

    /**
     * SEARCH  /_search/item-orders?query=:query : search for the itemOrder corresponding
     * to the query.
     *
     * @param query the query of the itemOrder search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/item-orders")
    @Timed
    public ResponseEntity<List<OrderItem>> searchItemOrders(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ItemOrders for query {}", query);
        Page<OrderItem> page = itemOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/item-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
