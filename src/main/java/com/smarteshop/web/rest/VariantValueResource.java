package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.VariantValue;
import com.smarteshop.service.VariantValueService;
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
 * REST controller for managing VariantValue.
 */
@RestController
@RequestMapping("/api")
public class VariantValueResource {

    private final Logger log = LoggerFactory.getLogger(VariantValueResource.class);
        
    @Inject
    private VariantValueService variantValueService;

    /**
     * POST  /variant-values : Create a new variantValue.
     *
     * @param variantValue the variantValue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new variantValue, or with status 400 (Bad Request) if the variantValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/variant-values")
    @Timed
    public ResponseEntity<VariantValue> createVariantValue(@RequestBody VariantValue variantValue) throws URISyntaxException {
        log.debug("REST request to save VariantValue : {}", variantValue);
        if (variantValue.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("variantValue", "idexists", "A new variantValue cannot already have an ID")).body(null);
        }
        VariantValue result = variantValueService.save(variantValue);
        return ResponseEntity.created(new URI("/api/variant-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("variantValue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /variant-values : Updates an existing variantValue.
     *
     * @param variantValue the variantValue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated variantValue,
     * or with status 400 (Bad Request) if the variantValue is not valid,
     * or with status 500 (Internal Server Error) if the variantValue couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/variant-values")
    @Timed
    public ResponseEntity<VariantValue> updateVariantValue(@RequestBody VariantValue variantValue) throws URISyntaxException {
        log.debug("REST request to update VariantValue : {}", variantValue);
        if (variantValue.getId() == null) {
            return createVariantValue(variantValue);
        }
        VariantValue result = variantValueService.save(variantValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("variantValue", variantValue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /variant-values : get all the variantValues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of variantValues in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/variant-values")
    @Timed
    public ResponseEntity<List<VariantValue>> getAllVariantValues(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VariantValues");
        Page<VariantValue> page = variantValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/variant-values");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /variant-values/:id : get the "id" variantValue.
     *
     * @param id the id of the variantValue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the variantValue, or with status 404 (Not Found)
     */
    @GetMapping("/variant-values/{id}")
    @Timed
    public ResponseEntity<VariantValue> getVariantValue(@PathVariable Long id) {
        log.debug("REST request to get VariantValue : {}", id);
        VariantValue variantValue = variantValueService.findOne(id);
        return Optional.ofNullable(variantValue)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /variant-values/:id : delete the "id" variantValue.
     *
     * @param id the id of the variantValue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/variant-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteVariantValue(@PathVariable Long id) {
        log.debug("REST request to delete VariantValue : {}", id);
        variantValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("variantValue", id.toString())).build();
    }

    /**
     * SEARCH  /_search/variant-values?query=:query : search for the variantValue corresponding
     * to the query.
     *
     * @param query the query of the variantValue search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/variant-values")
    @Timed
    public ResponseEntity<List<VariantValue>> searchVariantValues(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of VariantValues for query {}", query);
        Page<VariantValue> page = variantValueService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/variant-values");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
