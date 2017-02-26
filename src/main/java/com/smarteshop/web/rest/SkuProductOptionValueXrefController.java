package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.catalog.SkuProductOptionValueXref;
import com.smarteshop.service.SkuProductOptionValueXrefService;
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
 * REST controller for managing SkuProductOptionValueXref.
 */
@RestController
@RequestMapping("/api")
public class SkuProductOptionValueXrefController {

    private final Logger log = LoggerFactory.getLogger(SkuProductOptionValueXrefController.class);
        
    @Inject
    private SkuProductOptionValueXrefService skuProductOptionValueXrefService;

    /**
     * POST  /sku-product-option-value-xrefs : Create a new skuProductOptionValueXref.
     *
     * @param skuProductOptionValueXref the skuProductOptionValueXref to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skuProductOptionValueXref, or with status 400 (Bad Request) if the skuProductOptionValueXref has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sku-product-option-value-xrefs")
    @Timed
    public ResponseEntity<SkuProductOptionValueXref> createSkuProductOptionValueXref(@RequestBody SkuProductOptionValueXref skuProductOptionValueXref) throws URISyntaxException {
        log.debug("REST request to save SkuProductOptionValueXref : {}", skuProductOptionValueXref);
        if (skuProductOptionValueXref.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("skuProductOptionValueXref", "idexists", "A new skuProductOptionValueXref cannot already have an ID")).body(null);
        }
        SkuProductOptionValueXref result = skuProductOptionValueXrefService.save(skuProductOptionValueXref);
        return ResponseEntity.created(new URI("/api/sku-product-option-value-xrefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("skuProductOptionValueXref", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sku-product-option-value-xrefs : Updates an existing skuProductOptionValueXref.
     *
     * @param skuProductOptionValueXref the skuProductOptionValueXref to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skuProductOptionValueXref,
     * or with status 400 (Bad Request) if the skuProductOptionValueXref is not valid,
     * or with status 500 (Internal Server Error) if the skuProductOptionValueXref couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sku-product-option-value-xrefs")
    @Timed
    public ResponseEntity<SkuProductOptionValueXref> updateSkuProductOptionValueXref(@RequestBody SkuProductOptionValueXref skuProductOptionValueXref) throws URISyntaxException {
        log.debug("REST request to update SkuProductOptionValueXref : {}", skuProductOptionValueXref);
        if (skuProductOptionValueXref.getId() == null) {
            return createSkuProductOptionValueXref(skuProductOptionValueXref);
        }
        SkuProductOptionValueXref result = skuProductOptionValueXrefService.save(skuProductOptionValueXref);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("skuProductOptionValueXref", skuProductOptionValueXref.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sku-product-option-value-xrefs : get all the skuProductOptionValueXrefs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of skuProductOptionValueXrefs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sku-product-option-value-xrefs")
    @Timed
    public ResponseEntity<List<SkuProductOptionValueXref>> getAllSkuProductOptionValueXrefs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SkuProductOptionValueXrefs");
        Page<SkuProductOptionValueXref> page = skuProductOptionValueXrefService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sku-product-option-value-xrefs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sku-product-option-value-xrefs/:id : get the "id" skuProductOptionValueXref.
     *
     * @param id the id of the skuProductOptionValueXref to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skuProductOptionValueXref, or with status 404 (Not Found)
     */
    @GetMapping("/sku-product-option-value-xrefs/{id}")
    @Timed
    public ResponseEntity<SkuProductOptionValueXref> getSkuProductOptionValueXref(@PathVariable Long id) {
        log.debug("REST request to get SkuProductOptionValueXref : {}", id);
        SkuProductOptionValueXref skuProductOptionValueXref = skuProductOptionValueXrefService.findOne(id);
        return Optional.ofNullable(skuProductOptionValueXref)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sku-product-option-value-xrefs/:id : delete the "id" skuProductOptionValueXref.
     *
     * @param id the id of the skuProductOptionValueXref to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sku-product-option-value-xrefs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkuProductOptionValueXref(@PathVariable Long id) {
        log.debug("REST request to delete SkuProductOptionValueXref : {}", id);
        skuProductOptionValueXrefService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("skuProductOptionValueXref", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sku-product-option-value-xrefs?query=:query : search for the skuProductOptionValueXref corresponding
     * to the query.
     *
     * @param query the query of the skuProductOptionValueXref search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sku-product-option-value-xrefs")
    @Timed
    public ResponseEntity<List<SkuProductOptionValueXref>> searchSkuProductOptionValueXrefs(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SkuProductOptionValueXrefs for query {}", query);
        Page<SkuProductOptionValueXref> page = skuProductOptionValueXrefService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sku-product-option-value-xrefs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
