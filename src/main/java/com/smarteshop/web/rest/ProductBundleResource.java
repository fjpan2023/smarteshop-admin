package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.ProductBundle;
import com.smarteshop.service.ProductBundleService;
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
 * REST controller for managing ProductBundle.
 */
@RestController
@RequestMapping("/api")
public class ProductBundleResource {

    private final Logger log = LoggerFactory.getLogger(ProductBundleResource.class);
        
    @Inject
    private ProductBundleService productBundleService;

    /**
     * POST  /product-bundles : Create a new productBundle.
     *
     * @param productBundle the productBundle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productBundle, or with status 400 (Bad Request) if the productBundle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-bundles")
    @Timed
    public ResponseEntity<ProductBundle> createProductBundle(@RequestBody ProductBundle productBundle) throws URISyntaxException {
        log.debug("REST request to save ProductBundle : {}", productBundle);
        if (productBundle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productBundle", "idexists", "A new productBundle cannot already have an ID")).body(null);
        }
        ProductBundle result = productBundleService.save(productBundle);
        return ResponseEntity.created(new URI("/api/product-bundles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productBundle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-bundles : Updates an existing productBundle.
     *
     * @param productBundle the productBundle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productBundle,
     * or with status 400 (Bad Request) if the productBundle is not valid,
     * or with status 500 (Internal Server Error) if the productBundle couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-bundles")
    @Timed
    public ResponseEntity<ProductBundle> updateProductBundle(@RequestBody ProductBundle productBundle) throws URISyntaxException {
        log.debug("REST request to update ProductBundle : {}", productBundle);
        if (productBundle.getId() == null) {
            return createProductBundle(productBundle);
        }
        ProductBundle result = productBundleService.save(productBundle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productBundle", productBundle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-bundles : get all the productBundles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productBundles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-bundles")
    @Timed
    public ResponseEntity<List<ProductBundle>> getAllProductBundles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductBundles");
        Page<ProductBundle> page = productBundleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-bundles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-bundles/:id : get the "id" productBundle.
     *
     * @param id the id of the productBundle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productBundle, or with status 404 (Not Found)
     */
    @GetMapping("/product-bundles/{id}")
    @Timed
    public ResponseEntity<ProductBundle> getProductBundle(@PathVariable Long id) {
        log.debug("REST request to get ProductBundle : {}", id);
        ProductBundle productBundle = productBundleService.findOne(id);
        return Optional.ofNullable(productBundle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /product-bundles/:id : delete the "id" productBundle.
     *
     * @param id the id of the productBundle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-bundles/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductBundle(@PathVariable Long id) {
        log.debug("REST request to delete ProductBundle : {}", id);
        productBundleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productBundle", id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-bundles?query=:query : search for the productBundle corresponding
     * to the query.
     *
     * @param query the query of the productBundle search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/product-bundles")
    @Timed
    public ResponseEntity<List<ProductBundle>> searchProductBundles(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ProductBundles for query {}", query);
        Page<ProductBundle> page = productBundleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/product-bundles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
