package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.FulfillmentGroupItem;
import com.smarteshop.service.FulfillmentGroupItemService;
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
 * REST controller for managing FulfillmentGroupItem.
 */
@RestController
@RequestMapping("/api")
public class FulfillmentGroupItemResource {

    private final Logger log = LoggerFactory.getLogger(FulfillmentGroupItemResource.class);
        
    @Inject
    private FulfillmentGroupItemService fulfillmentGroupItemService;

    /**
     * POST  /fulfillment-group-items : Create a new fulfillmentGroupItem.
     *
     * @param fulfillmentGroupItem the fulfillmentGroupItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fulfillmentGroupItem, or with status 400 (Bad Request) if the fulfillmentGroupItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fulfillment-group-items")
    @Timed
    public ResponseEntity<FulfillmentGroupItem> createFulfillmentGroupItem(@RequestBody FulfillmentGroupItem fulfillmentGroupItem) throws URISyntaxException {
        log.debug("REST request to save FulfillmentGroupItem : {}", fulfillmentGroupItem);
        if (fulfillmentGroupItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fulfillmentGroupItem", "idexists", "A new fulfillmentGroupItem cannot already have an ID")).body(null);
        }
        FulfillmentGroupItem result = fulfillmentGroupItemService.save(fulfillmentGroupItem);
        return ResponseEntity.created(new URI("/api/fulfillment-group-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fulfillmentGroupItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fulfillment-group-items : Updates an existing fulfillmentGroupItem.
     *
     * @param fulfillmentGroupItem the fulfillmentGroupItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fulfillmentGroupItem,
     * or with status 400 (Bad Request) if the fulfillmentGroupItem is not valid,
     * or with status 500 (Internal Server Error) if the fulfillmentGroupItem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fulfillment-group-items")
    @Timed
    public ResponseEntity<FulfillmentGroupItem> updateFulfillmentGroupItem(@RequestBody FulfillmentGroupItem fulfillmentGroupItem) throws URISyntaxException {
        log.debug("REST request to update FulfillmentGroupItem : {}", fulfillmentGroupItem);
        if (fulfillmentGroupItem.getId() == null) {
            return createFulfillmentGroupItem(fulfillmentGroupItem);
        }
        FulfillmentGroupItem result = fulfillmentGroupItemService.save(fulfillmentGroupItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fulfillmentGroupItem", fulfillmentGroupItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fulfillment-group-items : get all the fulfillmentGroupItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fulfillmentGroupItems in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/fulfillment-group-items")
    @Timed
    public ResponseEntity<List<FulfillmentGroupItem>> getAllFulfillmentGroupItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FulfillmentGroupItems");
        Page<FulfillmentGroupItem> page = fulfillmentGroupItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fulfillment-group-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fulfillment-group-items/:id : get the "id" fulfillmentGroupItem.
     *
     * @param id the id of the fulfillmentGroupItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fulfillmentGroupItem, or with status 404 (Not Found)
     */
    @GetMapping("/fulfillment-group-items/{id}")
    @Timed
    public ResponseEntity<FulfillmentGroupItem> getFulfillmentGroupItem(@PathVariable Long id) {
        log.debug("REST request to get FulfillmentGroupItem : {}", id);
        FulfillmentGroupItem fulfillmentGroupItem = fulfillmentGroupItemService.findOne(id);
        return Optional.ofNullable(fulfillmentGroupItem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fulfillment-group-items/:id : delete the "id" fulfillmentGroupItem.
     *
     * @param id the id of the fulfillmentGroupItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fulfillment-group-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteFulfillmentGroupItem(@PathVariable Long id) {
        log.debug("REST request to delete FulfillmentGroupItem : {}", id);
        fulfillmentGroupItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fulfillmentGroupItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/fulfillment-group-items?query=:query : search for the fulfillmentGroupItem corresponding
     * to the query.
     *
     * @param query the query of the fulfillmentGroupItem search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/fulfillment-group-items")
    @Timed
    public ResponseEntity<List<FulfillmentGroupItem>> searchFulfillmentGroupItems(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of FulfillmentGroupItems for query {}", query);
        Page<FulfillmentGroupItem> page = fulfillmentGroupItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fulfillment-group-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
