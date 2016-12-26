package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.Sku;
import com.smarteshop.service.SkuService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Sku.
 */
@RestController
@RequestMapping("/api")
public class SkuController extends AbstractController<Sku>{

    private final Logger log = LoggerFactory.getLogger(SkuController.class);

    @Inject
    private SkuService skuService;

    /**
     * POST  /skus : Create a new sku.
     *
     * @param sku the sku to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sku, or with status 400 (Bad Request) if the sku has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skus")
    @Timed
    public ResponseEntity<Sku> createSku(@Valid @RequestBody Sku sku) throws URISyntaxException {
        log.debug("REST request to save Sku : {}", sku);
        if (sku.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sku", "idexists", "A new sku cannot already have an ID")).body(null);
        }
        Sku result = skuService.save(sku);
        return ResponseEntity.created(new URI("/api/skus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sku", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skus : Updates an existing sku.
     *
     * @param sku the sku to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sku,
     * or with status 400 (Bad Request) if the sku is not valid,
     * or with status 500 (Internal Server Error) if the sku couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skus")
    @Timed
    public ResponseEntity<Sku> updateSku(@Valid @RequestBody Sku sku) throws URISyntaxException {
        log.debug("REST request to update Sku : {}", sku);
        if (sku.getId() == null) {
            return createSku(sku);
        }
        Sku result = skuService.save(sku);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sku", sku.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skus : get all the skus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of skus in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/skus")
    @Timed
    public ResponseEntity<List<Sku>> getAllSkus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Skus");
        Page<Sku> page = skuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /skus/:id : get the "id" sku.
     *
     * @param id the id of the sku to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sku, or with status 404 (Not Found)
     */
    @GetMapping("/skus/{id}")
    @Timed
    public ResponseEntity<Sku> getSku(@PathVariable Long id) {
        log.debug("REST request to get Sku : {}", id);
        Sku sku = skuService.findOne(id);
        return Optional.ofNullable(sku)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /skus/:id : delete the "id" sku.
     *
     * @param id the id of the sku to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skus/{id}")
    @Timed
    public ResponseEntity<Void> deleteSku(@PathVariable Long id) {
        log.debug("REST request to delete Sku : {}", id);
        skuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sku", id.toString())).build();
    }

    /**
     * SEARCH  /_search/skus?query=:query : search for the sku corresponding
     * to the query.
     *
     * @param query the query of the sku search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/skus")
    @Timed
    public ResponseEntity<List<Sku>> searchSkus(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Skus for query {}", query);
        Page<Sku> page = skuService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/skus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
