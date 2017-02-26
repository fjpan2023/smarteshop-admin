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
import com.smarteshop.domain.catalog.RelatedProduct;
import com.smarteshop.service.RelatedProductService;
import com.smarteshop.web.common.AbstractController;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;

/**
 * REST controller for managing RelatedProduct.
 */
@RestController
@RequestMapping("/api")
public class RelatedProductController extends AbstractController<RelatedProduct>{

    private final Logger log = LoggerFactory.getLogger(RelatedProductController.class);

    @Inject
    private RelatedProductService relatedProductService;

    /**
     * POST  /related-products : Create a new relatedProduct.
     *
     * @param relatedProduct the relatedProduct to create
     * @return the ResponseEntity with status 201 (Created) and with body the new relatedProduct, or with status 400 (Bad Request) if the relatedProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/related-products")
    @Timed
    public ResponseEntity<RelatedProduct> createRelatedProduct(@Valid @RequestBody RelatedProduct relatedProduct) throws URISyntaxException {
        log.debug("REST request to save RelatedProduct : {}", relatedProduct);
        if (relatedProduct.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("relatedProduct", "idexists", "A new relatedProduct cannot already have an ID")).body(null);
        }
        RelatedProduct result = relatedProductService.save(relatedProduct);
        return ResponseEntity.created(new URI("/api/related-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("relatedProduct", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /related-products : Updates an existing relatedProduct.
     *
     * @param relatedProduct the relatedProduct to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated relatedProduct,
     * or with status 400 (Bad Request) if the relatedProduct is not valid,
     * or with status 500 (Internal Server Error) if the relatedProduct couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/related-products")
    @Timed
    public ResponseEntity<RelatedProduct> updateRelatedProduct(@Valid @RequestBody RelatedProduct relatedProduct) throws URISyntaxException {
        log.debug("REST request to update RelatedProduct : {}", relatedProduct);
        if (relatedProduct.getId() == null) {
            return createRelatedProduct(relatedProduct);
        }
        RelatedProduct result = relatedProductService.save(relatedProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("relatedProduct", relatedProduct.getId().toString()))
            .body(result);
    }

    /**
     * GET  /related-products : get all the relatedProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of relatedProducts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/related-products")
    @Timed
    public ResponseEntity<List<RelatedProduct>> getAllRelatedProducts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RelatedProducts");
        Page<RelatedProduct> page = relatedProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/related-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /related-products/:id : get the "id" relatedProduct.
     *
     * @param id the id of the relatedProduct to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the relatedProduct, or with status 404 (Not Found)
     */
    @GetMapping("/related-products/{id}")
    @Timed
    public ResponseEntity<RelatedProduct> getRelatedProduct(@PathVariable Long id) {
        log.debug("REST request to get RelatedProduct : {}", id);
        RelatedProduct relatedProduct = relatedProductService.findOne(id);
        return Optional.ofNullable(relatedProduct)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /related-products/:id : delete the "id" relatedProduct.
     *
     * @param id the id of the relatedProduct to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/related-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteRelatedProduct(@PathVariable Long id) {
        log.debug("REST request to delete RelatedProduct : {}", id);
        relatedProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("relatedProduct", id.toString())).build();
    }

    /**
     * SEARCH  /_search/related-products?query=:query : search for the relatedProduct corresponding
     * to the query.
     *
     * @param query the query of the relatedProduct search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/related-products")
    @Timed
    public ResponseEntity<List<RelatedProduct>> searchRelatedProducts(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of RelatedProducts for query {}", query);
        Page<RelatedProduct> page = relatedProductService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/related-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
