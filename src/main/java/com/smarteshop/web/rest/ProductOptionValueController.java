package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.ProductOptionValue;
import com.smarteshop.service.ProductOptionValueService;
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
 * REST controller for managing ProductOptionValue.
 */
@RestController
@RequestMapping("/api/product-option-values")
public class ProductOptionValueController {

    private final Logger log = LoggerFactory.getLogger(ProductOptionValueController.class);
        
    @Inject
    private ProductOptionValueService productOptionValueService;

    /**
     * POST  /product-option-values : Create a new productOptionValue.
     *
     * @param productOptionValue the productOptionValue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productOptionValue, or with status 400 (Bad Request) if the productOptionValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PostMapping()
    public ResponseEntity<ProductOptionValue> createProductOptionValue(@RequestBody ProductOptionValue productOptionValue) throws URISyntaxException {
        log.debug("REST request to save ProductOptionValue : {}", productOptionValue);
        if (productOptionValue.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productOptionValue", "idexists", "A new productOptionValue cannot already have an ID")).body(null);
        }
        ProductOptionValue result = productOptionValueService.save(productOptionValue);
        return ResponseEntity.created(new URI("/api/product-option-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productOptionValue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-option-values : Updates an existing productOptionValue.
     *
     * @param productOptionValue the productOptionValue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productOptionValue,
     * or with status 400 (Bad Request) if the productOptionValue is not valid,
     * or with status 500 (Internal Server Error) if the productOptionValue couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @PutMapping()
    public ResponseEntity<ProductOptionValue> updateProductOptionValue(@RequestBody ProductOptionValue productOptionValue) throws URISyntaxException {
        log.debug("REST request to update ProductOptionValue : {}", productOptionValue);
        if (productOptionValue.getId() == null) {
            return createProductOptionValue(productOptionValue);
        }
        ProductOptionValue result = productOptionValueService.save(productOptionValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productOptionValue", productOptionValue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-option-values : get all the productOptionValues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productOptionValues in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping()
    @Timed
    public ResponseEntity<List<ProductOptionValue>> getAllProductOptionValues(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductOptionValues");
        Page<ProductOptionValue> page = productOptionValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-option-values");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-option-values/:id : get the "id" productOptionValue.
     *
     * @param id the id of the productOptionValue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productOptionValue, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/{id}")
    public ResponseEntity<ProductOptionValue> getProductOptionValue(@PathVariable Long id) {
        log.debug("REST request to get ProductOptionValue : {}", id);
        ProductOptionValue productOptionValue = productOptionValueService.findOne(id);
        return Optional.ofNullable(productOptionValue)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /product-option-values/:id : delete the "id" productOptionValue.
     *
     * @param id the id of the productOptionValue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Timed
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductOptionValue(@PathVariable Long id) {
        log.debug("REST request to delete ProductOptionValue : {}", id);
        productOptionValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productOptionValue", id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-option-values?query=:query : search for the productOptionValue corresponding
     * to the query.
     *
     * @param query the query of the productOptionValue search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search")
    @Timed
    public ResponseEntity<List<ProductOptionValue>> searchProductOptionValues(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ProductOptionValues for query {}", query);
        Page<ProductOptionValue> page = productOptionValueService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/product-option-values");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
