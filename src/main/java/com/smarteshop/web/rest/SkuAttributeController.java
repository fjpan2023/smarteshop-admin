package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.catalog.SkuAttribute;
import com.smarteshop.service.SkuAttributeService;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SkuAttribute.
 */
@RestController
@RequestMapping("/api")
public class SkuAttributeController {

    private final Logger log = LoggerFactory.getLogger(SkuAttributeController.class);
        
    @Inject
    private SkuAttributeService skuAttributeService;

    /**
     * POST  /sku-attributes : Create a new skuAttribute.
     *
     * @param skuAttribute the skuAttribute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skuAttribute, or with status 400 (Bad Request) if the skuAttribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sku-attributes")
    @Timed
    public ResponseEntity<SkuAttribute> createSkuAttribute(@Valid @RequestBody SkuAttribute skuAttribute) throws URISyntaxException {
        log.debug("REST request to save SkuAttribute : {}", skuAttribute);
        if (skuAttribute.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("skuAttribute", "idexists", "A new skuAttribute cannot already have an ID")).body(null);
        }
        SkuAttribute result = skuAttributeService.save(skuAttribute);
        return ResponseEntity.created(new URI("/api/sku-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("skuAttribute", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sku-attributes : Updates an existing skuAttribute.
     *
     * @param skuAttribute the skuAttribute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skuAttribute,
     * or with status 400 (Bad Request) if the skuAttribute is not valid,
     * or with status 500 (Internal Server Error) if the skuAttribute couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sku-attributes")
    @Timed
    public ResponseEntity<SkuAttribute> updateSkuAttribute(@Valid @RequestBody SkuAttribute skuAttribute) throws URISyntaxException {
        log.debug("REST request to update SkuAttribute : {}", skuAttribute);
        if (skuAttribute.getId() == null) {
            return createSkuAttribute(skuAttribute);
        }
        SkuAttribute result = skuAttributeService.save(skuAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("skuAttribute", skuAttribute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sku-attributes : get all the skuAttributes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of skuAttributes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sku-attributes")
    @Timed
    public ResponseEntity<List<SkuAttribute>> getAllSkuAttributes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SkuAttributes");
        Page<SkuAttribute> page = skuAttributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sku-attributes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sku-attributes/:id : get the "id" skuAttribute.
     *
     * @param id the id of the skuAttribute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skuAttribute, or with status 404 (Not Found)
     */
    @GetMapping("/sku-attributes/{id}")
    @Timed
    public ResponseEntity<SkuAttribute> getSkuAttribute(@PathVariable Long id) {
        log.debug("REST request to get SkuAttribute : {}", id);
        SkuAttribute skuAttribute = skuAttributeService.findOne(id);
        return Optional.ofNullable(skuAttribute)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sku-attributes/:id : delete the "id" skuAttribute.
     *
     * @param id the id of the skuAttribute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sku-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkuAttribute(@PathVariable Long id) {
        log.debug("REST request to delete SkuAttribute : {}", id);
        skuAttributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("skuAttribute", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sku-attributes?query=:query : search for the skuAttribute corresponding
     * to the query.
     *
     * @param query the query of the skuAttribute search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sku-attributes")
    @Timed
    public ResponseEntity<List<SkuAttribute>> searchSkuAttributes(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SkuAttributes for query {}", query);
        Page<SkuAttribute> page = skuAttributeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sku-attributes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
