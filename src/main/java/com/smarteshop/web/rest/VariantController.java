package com.smarteshop.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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
import com.smarteshop.domain.Variant;
import com.smarteshop.domain.VariantValue;
import com.smarteshop.service.VariantService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Variant.
 */
@RestController
@RequestMapping("/api/variants")
public class VariantController  extends AbstractController<Variant>{

    private final Logger log = LoggerFactory.getLogger(VariantController.class);

    @Inject
    private VariantService variantService;

    /**
     * POST  /variants : Create a new variant.
     *
     * @param variant the variant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new variant, or with status 400 (Bad Request) if the variant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping()
    @Timed
    public ResponseEntity<Variant> createVariant(@RequestBody Variant variant) throws URISyntaxException {
        log.debug("REST request to save Variant : {}", variant);
        if (variant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("variant", "idexists", "A new variant cannot already have an ID")).body(null);
        }
        Variant result = variantService.save(variant);
        return ResponseEntity.created(new URI("/api/variants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("variant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /variants : Updates an existing variant.
     *
     * @param variant the variant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated variant,
     * or with status 400 (Bad Request) if the variant is not valid,
     * or with status 500 (Internal Server Error) if the variant couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping()
    @Timed
    public ResponseEntity<Variant> updateVariant(@RequestBody Variant variant) throws URISyntaxException {
        log.debug("REST request to update Variant : {}", variant);
        if (variant.getId() == null) {
            return createVariant(variant);
        }
        Variant result = variantService.save(variant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("variant", variant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /variants : get all the variants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of variants in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping()
    @Timed
    public ResponseEntity<List<Variant>> getAllVariants(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Variants");
        Page<Variant> page = variantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/variants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /variants/:id : get the "id" variant.
     *
     * @param id the id of the variant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the variant, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<Variant> getVariant(@PathVariable Long id) {
        log.debug("REST request to get Variant : {}", id);
        Variant variant = variantService.findOne(id);
        return Optional.ofNullable(variant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /variants/:id : delete the "id" variant.
     *
     * @param id the id of the variant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        log.debug("REST request to delete Variant : {}", id);
        variantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("variant", id.toString())).build();
    }

    /**
     * SEARCH  /_search/variants?query=:query : search for the variant corresponding
     * to the query.
     *
     * @param query the query of the variant search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search")
    @Timed
    public ResponseEntity<List<Variant>> searchVariants(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Variants for query {}", query);
        Page<Variant> page = variantService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/variants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
